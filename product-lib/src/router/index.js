import Vue from 'vue'
import Router from 'vue-router'
// import Store from '@/components/Store'
import ProductList from '@/components/ProductList'
import StoreList from '@/components/StoreList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'StoreList',
      component: StoreList
    },
    {
      path: '/stores/:id',
      name: 'ProductList',
      component: ProductList
    }
  ]
})
