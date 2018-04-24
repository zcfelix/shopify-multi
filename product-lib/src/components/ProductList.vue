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
    this.getProducts(this.getPrice);
  },
  methods: {
    getProducts(getPrice) {
      axios
        .get("http://localhost/products")
        .then(resp => {
          this.products = resp.data.data;
          this.products.forEach(p => {
            getPrice(p.id);
            p.image = "//placehold.it/200";
          });
          console.log(this.products);
        })
        .catch(error => console.error("error in getting product list"));
    },
    getPrice(productId) {
      axios
        .get(`http://localhost/products/${productId}/current-price`)
        .then(resp => {
          this.products.find(p => p.id === productId).price = resp.data.data.amount;
        })
        .catch(error => console.error("error in gettting product price"));
    }
  }
};
</script>
<style>
/* Nothing for now */
</style>