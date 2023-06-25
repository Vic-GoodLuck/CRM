import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "@/assets/elementStyle/style.css"
// 引入路由相关组件
import router from './router'
// 引入仓库进行注册
import store from './store'
// 引入axios
import axios from 'axios'
import './style/md.css'
// 
Vue.prototype.$http = axios
axios.defaults.baseURL = '/api'
// axios.defaults.baseURL = 'http://1/01.43.104.20:8182'
axios.defaults.timeout = 10000
// 加载全局样式
import './style/index.less'
import './assets/font/iconfont.css'
// import commenlist from '@/components/commenlist'
import Video from 'video.js'
import 'video.js/dist/video-js.css'
import VueEmoji from "emoji-vue";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import Vue2Editor from "vue2-editor";

// import BootstrapVue from 'bootstrap-vue'
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
//
// Vue.use(BootstrapVue)

Vue.use(Vue2Editor);
// use
Vue.use(mavonEditor)
Vue.component(VueEmoji.name,VueEmoji)
Vue.prototype.$videos = Video
// Vue.component(commenlist.name,commenlist)
Vue.use(ElementUI,{size:"small"});
Vue.config.productionTip = false
axios.defaults.withCredentials = true // session跨域
new Vue({
  render: h => h(App),
  router,store
}).$mount('#app')
