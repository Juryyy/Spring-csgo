from locust import HttpUser, task, between

class WebsiteUser(HttpUser):
    wait_time = between(1, 2.5)

    @task
    def get_matches(self):
        self.client.get("/matches")

    @task
    def get_match_by_id(self):
        self.client.get("/matches/1")

    @task
    def get_matches_count_for_each_map(self):
        self.client.get("/matches/maps")

    @task
    def get_matches_by_map_name(self):
        self.client.get("/matches/map/de_mirage") 

    @task
    def get_list_of_maps_with_matches_with_highest_rounds(self):
        self.client.get("/matches/top-map-matches")

    @task
    def get_all_rounds(self):
        self.client.get("/rounds")

    @task
    def get_rounds_count_and_map_for_each_match(self):
        self.client.get("/rounds/counts")

    @task
    def get_rounds_count_for_each_map(self):
        self.client.get("/rounds/maps")

    @task
    def get_average_rounds_count_for_all_matches(self):
        self.client.get("/rounds/avg")

    @task
    def get_average_rounds_count_for_matches_on_specific_map(self):
        self.client.get("/rounds/avg/de_mirage")  
    @task
    def get_winrate_for_each_team_on_specific_map(self):
        self.client.get("/rounds/winrate/de_mirage") 

    @task
    def get_average_kills_count_for_each_map(self):
        self.client.get("/kills/avg")

    @task
    def get_average_kills_count_for_matches_on_specific_map(self):
        self.client.get("/kills/avg/de_mirage") 

    @task
    def get_average_kills_count_for_matches_on_specific_side(self):
        self.client.get("/kills/avg/sides")

    @task
    def get_average_kills_count_for_matches_with_each_weapon(self):
        self.client.get("/kills/weapons")
