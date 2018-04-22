<template>
  <div class="container my-5">
    <h1 class="text-center">Our products</h1>
    <div class="row">
      <product v-for="product in products" 
      :key="product.id"
      :id="product.id"
      :name="product.name"
      :price="product.price"
      :image="product.image"
      :description="product.description"/>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Product from "./Product";
export default {
  name: "product-list",
  components: { Product },
  data() {
    return {
      products: []
    };
  },
  created() {
    this.getProducts();
  },
  methods: {
    getProducts() {
      axios
        .get("http://localhost/products")
        .then(resp => {
          console.log(resp.data);
          this.products = resp.data.data;
          this.products.forEach(s => s.image = "//placehold.it/200");
        })
        .catch(error => console.error("error in getting store list"));
    }
  }
};
</script>
<style>
/* Nothing for now */
</style>