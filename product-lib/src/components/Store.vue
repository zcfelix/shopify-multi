<template>
  <div class="col-md-3">
    <div class="card">
      <img :src="image" :alt="name" class="card-img-top">
      <div class="card-body">
        <h4 class="card-title">{{ name }}</h4>
        <div class="card-text">{{ description }}</div>
        <div class="row justify-content-end">
          <button class="btn btn-primary">Enter</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "store",
  props: ['id', 'name', 'description', 'image']
};
</script>

