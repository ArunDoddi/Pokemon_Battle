const app = {
    data() {
        return {
            pokemonList: [], // List of Pokémon loaded from the data.csv file
            pokemonA: '', // Selected Pokémon A
            pokemonB: '', // Selected Pokémon B
            battleResult: null, // Result of the battle
            loading: false // To show a loading spinner while fetching data
        };
    },
    methods: {
        async fetchPokemonData() {
            try {
                const response = await axios.get('/pokemon/data');
                this.pokemonList = response.data; // Store the Pokémon list
            } catch (error) {
                console.error('Error loading Pokemon data:', error);
                alert('Failed to load Pokemon data.');
            } finally {
                this.loading = false; // Hide loading spinner
            }
        },
        async battle() {
            if (!this.pokemonA || !this.pokemonB) {
                alert('Please select two Pokémon to battle.');
                return;
            } else if (this.pokemonA === this.pokemonB) {
                alert('Please select two different Pokémon.');
                return;
            }

            // Hit the battle API with selected Pokémon
            try {
                this.loading = true;
                const response = await axios.get('/attack', {
                    params: {
                        pokemonA: this.pokemonA,
                        pokemonB: this.pokemonB
                    }
                });
                this.battleResult = response.data; // Store the API result
            } catch (error) {
                console.error('Error during the battle:', error);
                alert('An error occurred while performing the battle.');
            } finally {
                this.loading = false;
            }
        }
    },
    async mounted() {
        await this.fetchPokemonData(); // Fetch the Pokémon list on page load
    }
};

// Create and mount the Vue app
Vue.createApp(app).mount('#app');
  
  