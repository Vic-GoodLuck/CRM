<template>
    <div>
      <el-form :model="form2" label-position="left" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form2.clientLinkmanName"></el-input>
        </el-form-item>
        <el-form-item label="性别">
            <el-radio-group v-model.number="form2.clientLinkmanSex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form2.clientLinkmanJob"></el-input>
        </el-form-item>
        <el-form-item label="办公电话">
          <el-input v-model="form2.clientLinkmanTel"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="form2.clientLinkmanMobile"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form2.remark"></el-input>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script>
  export default {
    data() {
      return {
        form2: {
            clientLinkmanName:'',
            clientLinkmanSex:0,
            clientLinkmanJob:'',
            clientLinkmanTel:'',
            clientLinkmanMobile:'',
            remark:''
        }
      };
    },
    props: {
        form2: {
        type: Object,
        default: () => ({}),
        },
    },
    watch: {
        form2: {
            deep: true,
            handler() {
                this.$emit('update:form2', this.form2);
            },
        },
    },
    methods: {
        loadContactData(contactData) {
        for (const key in contactData) {
            this.$set(this.form2, key, contactData[key]);
        }
    },
  },
  };
  </script>