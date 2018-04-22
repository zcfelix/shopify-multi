<template>
  <div id="app" class="container my-5">
    <h1 class="text-center">Stores</h1>
    <div class="row">
      <store v-for="store in stores" 
      :key="store.id"
      :id="store.id"
      :name="store.name"
      :image="store.image"
      :description="store.description"/>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Store from "./Store";
export default {
  name: "store-list",
  components: { Store },
  data() {
    return {
      stores: []
    };
  },
  created() {
    this.getStores();
  },
  methods: {
    getStores() {
      axios
        .get("http://localhost/stores")
        .then(resp => {
          this.stores = resp.data.data;
          this.stores.forEach(s => s.image = "//placehold.it/200");
        })
        .catch(error => console.error("error in getting store list"));
    }
  }
};
</script>
<style>
/* Nothing for now */
</style>