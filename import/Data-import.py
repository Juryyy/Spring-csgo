import csv
import psycopg2
import time

conn = psycopg2.connect("dbname=postgres user=postgres password=123 host=localhost port=5434")
cur = conn.cursor()


dataType = 'dmg' # kills, dmg, grenades, meta_demos

if dataType == 'meta_demos':
    with open('esea_meta_demos.part2.csv', 'r') as f:
        inserted_match_numbers = set()
        reader = csv.reader(f)
        next(reader) 
        for i, row in enumerate(reader):
            match_number = int(row[0].split('_')[2].split('.')[0])
            map_name = row[1]

            if match_number not in inserted_match_numbers:
                cur.execute(
                    "INSERT INTO matches (match_number, map) VALUES (%s, %s) ON CONFLICT (match_number) DO NOTHING",
                    (match_number, map_name)
                )
                inserted_match_numbers.add(match_number)

                if i % 10000 == 0:
                    print(f'Inserted {i} rows')

    conn.commit()
    cur.close()
    conn.close()

if dataType == 'kills':
    #for filename in ['esea_master_kills_demos.part1.csv', 'esea_master_kills_demos.part2.csv']:
        with open('esea_master_kills_demos.part1.csv', 'r') as f:
            reader = csv.reader(f)
            next(reader) 
            for i, row in enumerate(reader):
                match_number = int(row[0].split('_')[2].split('.')[0])

                # Look up the match ID in the matches table
                cur.execute(
                    "SELECT id FROM matches WHERE match_number = %s",
                    (match_number,)
                )
                result = cur.fetchone()
                if result is None:
                    continue
                match_id = result[0]

                # Extract the kill data from the row
                round_number = row[1]
                attack_side = row[6]
                victim_side = row[7]
                weapon = row[8]
                weapon_type = row[9]
                ct_alive = row[10]
                t_alive = row[11]
                is_bomb_planted = row[12].lower() == 'true'

                # Insert the kill data into the kills table
                cur.execute(
                    "INSERT INTO kills (match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)",
                    (match_id, round_number, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
                )

                if i % 5000000 == 0:
                    break

        conn.commit()
        cur.close()
        conn.close()

import csv

# Load the match numbers and IDs into a dictionary
match_ids = {}
cur.execute("SELECT match_number, id FROM matches")
for match_number, id in cur:
    match_ids[match_number] = id

# Process the CSV files
if dataType == 'dmg':
    for filename in ['esea_master_dmg_demos.part1.csv', 'esea_master_dmg_demos.part2.csv']:
        with open(filename, 'r') as f:
            reader = csv.reader(f)
            next(reader) 
            start_time = time.time()
            for i, row in enumerate(reader):
                match_number = int(row[0].split('_')[2].split('.')[0])

                # Look up the match ID in the dictionary
                match_id = match_ids.get(match_number)
                if match_id is None:
                    continue

                # Extract the damage data from the row
                round_number = row[1]
                attack_side = row[6] if row[6] != 'None' else 'Game'
                victim_side = row[7]
                hp_dmg = row[8]
                arm_dmg = row[9]
                is_bomb_planted = row[10].lower() == 'true'
                hitbox = row[12]
                weapon = row[13]
                weapon_type = row[14]

                # Insert the row into the database
                cur.execute(
                    "INSERT INTO damage (match_id, round, attack_side, victim_side, hp_dmg, arm_dmg, is_bomb_planted, hitbox, weapon, weapon_type) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                    (match_id, round_number, attack_side, victim_side, hp_dmg, arm_dmg, is_bomb_planted, hitbox, weapon, weapon_type)
                )

                if i % 5000000 == 0:
                    end_time = time.time()  # End the timer
                    elapsed_time = end_time - start_time
                    print(f'Processed {i} rows in {elapsed_time:.2f} seconds')
                    start_time = time.time()  # Restart the timer

    conn.commit()
    cur.close()
    conn.close()