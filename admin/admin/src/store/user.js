// import axios from 'axios'
const state ={
    userinfo:JSON.parse(localStorage.getItem('Userinfo')) || {}
}
const mutations = {
    GETUSERINFO(state,userinfo){
        state.userinfo = userinfo
    }
}
const actions ={
    async getUserinfo ({commit},data){
        const result = await this.$http.post('/vsp-car/user/login',this.loginForm )
        console.log(result)
        if(result.data.code ===200){
          this.$message.success('登陆成功')
          localStorage.setItem('Userinfo',JSON.stringify(result.data.obj))
          var userinfo = JSON.parse(localStorage.getItem('Userinfo'))
          this.$store.dispatch('getUserinfo',userinfo)
          this.$router.push('/page')
        }else{
          this.$message.error('请重新填写用户信息！')
          location.reload()
        }
        commit('GETUSERINFO',data)
        console.warn('localStorage.getItem', JSON.parse(localStorage.getItem('Userinfo')))
    }
}
const getters ={}
export default {
    state,actions,mutations,getters
}