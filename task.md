##### 服务拆分，形成API文档

| Work                    | Task                         | Plan(min) | Real(min) |
| ----------------------- | ---------------------------- | ------ | ------- |
| 服务拆分，形成API文档   | 四色建模，根据模型拆分服务   | done   | - |
|                         | 设计URI                      | 20 | 120 |
|                         |  |  |         |
|                         |                              |        |         |
| 脚手架                  | 搭建项目脚手架               | 20 | 40 |
| Store & Product Service | POST /stores                 | 15 | 270 |
|                         | GET   /stores                | 10 | 10 |
|                         | GET   /stores/{id}           | 10 | 25 |
|                             | POST /stores/{id}/products | 20 |40|
|  |                      GET /stores/{id}/products | 10 |14|
|   |                        PUT /stores/{id}/products/{id}/unloading | 10 |39|
| | PUT /stores/{id}/products/{id}/uploading | 10 |7|
| | GET /products | 20 |20|
| | GET /products/{id} | 10 |15|
| | 写服务的functional test | 30 ||
| | 看 spring eureka 文档，学习使用方法 | 60 ||
| | 部署store & product service 以及 eureka service | 60 |240|
| | 部署nginx proxy | 10 |10|
| | 手动验证服务 | 5 |10|
| | 学习vuejs | 120 ||
| | 看vuejs文档，实现自定义组件的方式 | 60 ||
| | 实现自己的vuejs自定义组件 | 90 ||
| | 利用bootstrap改造组件外观 | 60 ||
| | 学习 css in js并进行css改造 | 90 ||
| | 学习 npm 打包前端组件 | 60 ||
| |  |  ||
| Product Price Service | POST /products/{id}/prices | 30 |245|
| | GET   /products/{id}/prices | 10 |16|
| | GET   /products/{id}/prices/{id} | 5 |28|
| | GET   /products/{id}/current-price | 5 |31|
| | 写functional test | 20 ||
| | 服务部署验证 | 15 ||
| | 实现前端组件 | 40 ||
| | 利用 npm 打包前端组件 | 30 ||
| |  |  ||
| Inventory Service | POST /products/{id}/inventories | 20 ||
| | GET   /products/{id}/inventories/current | 10 ||
| | PATCH /products/{id}/inentories/current | 10 ||
| | 写 functional test | 20 ||
| | 服务部署验证 | 15 ||
| | 实现前端组件 | 40 ||
| | 利用npm 打包前端组件 | 30 ||
| |  |  ||
| Order Service | POST /orders | 25 ||
|  | GET  /orders | 15 ||
|  | GET /orders/{id} | 10 ||
|  | POST /orders/{id}/payment | 15 ||
|  | POST /orders/{id}/shipment | 15 ||
|  | POST /orders/{id}/receive-confirmation | 15 ||
|  | 写 functional test | 30 ||
|  | 服务部署验证 | 15 ||
|  | 实现前端组件 | 60 ||
|  | 利用 npm 打包前端组件 | 40 ||
|  |  |  ||
| Refund Service | POST /orders/{id}/returns | 30 ||
|  | GET  /orders/{id}/returns | 15 ||
|  | GET /orders/{id}/returns/{id} | 10 ||
|  | POST /orders/{id}/returns/{id}/return-confirmation | 15 ||
|  | 写 functional test | 30 ||
|  | 服务部署验证 | 15 ||
|  | 实现前端组件 | 60 ||
|  | 利用 npm 打包前端组件 | 20 ||
|  |  |  ||
| Customer Service | POST /customers | 20 ||
|  | GET  /customers/{id} | 10 ||
|  | PATCH /customers/{id} | 20 ||
|  | 写 functional test | 20 ||
|  | 服务部署验证 | 10 ||
|  | 实现前端组件 | 40 ||
|  | 利用npm打包前端组件 | 20 ||
|  |  |  ||
| Cart Service | POST /customers/{id}/cart/items | 20 ||
|  | PUT /customers/{id}/cart/items | 20 ||
|  | 写 functional test | 20 ||
|  | 服务部署验证 | 10 ||
|  | 实现前端组件 | 30 ||
|  | 利用npm打包前端组件 | 20 ||
|  |  |  ||
| nginx 配置 | 统一配置nginx | 40 ||
|  |  |  ||
| taotal |  | 1835 ||
