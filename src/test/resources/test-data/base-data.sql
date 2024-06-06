INSERT INTO public.matches (id, match_number, map) VALUES (1, 1001, 'de_dust2');
INSERT INTO public.matches (id, match_number, map) VALUES (2, 1002, 'de_inferno');
INSERT INTO public.matches (id, match_number, map) VALUES (3, 1003, 'de_mirage');
INSERT INTO public.matches (id, match_number, map) VALUES (4, 1004, 'de_dust2');

INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (1, 1, 1, 'Terrorist', 'CounterTerrorist', 'AK-47', 'Rifle', 4, 5, false);
INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (2, 2, 1, 'CounterTerrorist', 'Terrorist', 'M4A4', 'Rifle', 5, 4, false);
INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (3, 3, 1, 'Terrorist', 'CounterTerrorist', 'AWP', 'Sniper', 4, 5, true);
INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (4, 1, 2, 'CounterTerrorist', 'Terrorist', 'M4A4', 'Rifle', 5, 4, false);
INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (5, 2, 2, 'Terrorist', 'CounterTerrorist', 'AK-47', 'Rifle', 4, 5, true);
INSERT INTO public.kills (id, match_id, round, attack_side, victim_side, weapon, weapon_type, ct_alive, t_alive, is_bomb_planted)
VALUES (6, 3, 2, 'CounterTerrorist', 'Terrorist', 'AWP', 'Sniper', 5, 4, false);


INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (1, 1, 1, 'Terrorist', 4000, 5000);
INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (2, 2, 1, 'CounterTerrorist', 5000, 4000);
INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (3, 3, 1, 'Terrorist', 4000, 5000);
INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (4, 1, 2, 'CounterTerrorist', 5000, 4000);
INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (5, 2, 2, 'Terrorist', 4000, 5000);
INSERT INTO public.rounds (id, match_id, round, winner_side, ct_eq_val, t_eq_val)
VALUES (6, 3, 2, 'CounterTerrorist', 5000, 4000);

INSERT INTO public.damage (id, match_id, round, attack_side, victim_side, hp_dmg, arm_dmg, is_bomb_planted, hitbox, weapon, weapon_type)
VALUES (1, 1, 1, 'Terrorist', 'CounterTerrorist', 100, 0, false, 'Head', 'AK-47', 'Rifle');
INSERT INTO public.damage (id, match_id, round, attack_side, victim_side, hp_dmg, arm_dmg, is_bomb_planted, hitbox, weapon, weapon_type)
VALUES (2, 2, 1, 'CounterTerrorist', 'Terrorist', 80, 20, false, 'Chest', 'M4A4', 'Rifle');
INSERT INTO public.damage (id, match_id, round, attack_side, victim_side, hp_dmg, arm_dmg, is_bomb_planted, hitbox, weapon, weapon_type)
VALUES (3, 3, 1, 'Terrorist', 'CounterTerrorist', 100, 0, true, 'Head', 'AWP', 'Sniper');