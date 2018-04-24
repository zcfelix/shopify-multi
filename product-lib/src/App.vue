<template>
  <div>
    <router-view></router-view>
    <!-- <store-list></store-list> -->
    <!-- <product-list></product-list> -->
  </div>
</template>

<script>
import StoreList from "./components/StoreList";
import ProductList from "./components/ProductList";
export default {
  name: "app",
  components: { StoreList, ProductList }
};
</script>
<style>
/* Nothing for now */
</style>