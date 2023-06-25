<template>
    <div>
        <el-card class="box-card" style="width: 100%">
            <el-breadcrumb style="margin-bottom:15px;" separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/' }">客户管理</el-breadcrumb-item>
                <el-breadcrumb-item>客户流失管理</el-breadcrumb-item>
            </el-breadcrumb>
            <el-divider></el-divider>
            <div style="display: flex">
                <div style="padding: 10px 0;">
                    <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入客户名"
                        style="width: 160px;margin:5px;" v-model="clientName_select"></el-input>
                    <el-select v-model="clientAreaId_select" filterable placeholder="请选择客户所在地区"
                        style="width: 150px;margin:5px;">
                        <el-option v-for="item in area_options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>

                    <el-select v-model="clientLevelId_select" filterable placeholder="请选择客户等级"
                        style="width: 150px;margin:5px;">
                        <el-option v-for="item in level_options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>


                    <el-select v-model="clientContentment_select" filterable placeholder="请选择客户满意度"
                        style="width: 160px;margin:5px;">
                        <el-option v-for="item in Contentment_options" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>

                    <el-select v-model="clientCredit_select" filterable placeholder="请选择客户信用度"
                        style="width: 160px;margin:5px;">
                        <el-option v-for="item in credit_options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>

                    <el-select v-model="clientCustId_select" filterable placeholder="请选择负责经理"
                        style="width: 150px;margin:5px;">
                        <el-option v-for="item in cust_options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>


                    <el-button class="ml-5" type="primary" @click="conditionSelect_click">搜索</el-button>
                    <el-button class="ml-5" type="danger" @click="clearCondition">清空</el-button>
                    <!-- TODO: 导出功能尚未实现 -->
                    <el-button class="ml-5" type="success" @click="">导出</el-button>
                </div>
            </div>

            <!--      用户信息表格-->
            <el-table :data="tableData" border :row-style="{ height: '50px' }" :cell-style="{ padding: '0px' }"
                max-height="1000" style="width: 100%" @selection-change="handleSelectionChange">
                >
                <el-table-column prop="clientCode" label="客户编号" width="160">
                </el-table-column>
                <el-table-column prop="clientName" label="客户名称" width="160">
                </el-table-column>
                <el-table-column prop="clientAreaId" label="所在地区" width="140" :formatter="areaFormatter">
                </el-table-column>

                <el-table-column prop="clientLevelId" label="客户等级" width="160" :formatter="levelFormatter">
                </el-table-column>
                <el-table-column label="客户满意度" width="160">
                    <template slot-scope="scope">
                        <el-rate v-model="scope.row.clientContentment" :colors="colors" disabled>
                        </el-rate>
                    </template>
                </el-table-column>
                <el-table-column label="客户信用度" width="150">
                    <template slot-scope="scope">
                        <el-rate v-model="scope.row.clientCredit" :icon-classes="iconClasses"
                            void-icon-class="icon-rate-face-off" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" disabled>
                        </el-rate>
                    </template>
                </el-table-column>
                <el-table-column prop="clientCustId" label="负责客户经理名称" width="160" :formatter="custFormatter">
                </el-table-column>
                <el-table-column prop="countdown" label="暂缓流失" width="100" header-align="center">
                    <template slot-scope="{ row }">
                        <el-button @click="lostMethod(row.clientCode)" type="danger" size="small">暂缓流失</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <div class="block" style="margin-top: 20px;">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page.sync="currentPage" :page-size="pagesize" layout="prev, pager, next, jumper,->,total"
                    :total="total">
                </el-pagination>
            </div>

            <el-dialog title="暂缓流失" :visible.sync="dialogFormVisible1" width="50%" height="90%" append-to-body center
            :before-close="handleBeforeClose">
                <reprieve-lose :currentClientCode.sync="currentClientCode"
                    :dialogFormVisible1.sync="dialogFormVisible1"></reprieve-lose>
            </el-dialog>

        </el-card>
    </div>
</template>

<script>
import axios from "axios";
import { MessageBox } from 'element-ui';
import ReprieveLose from '../ReprieveLose/index.vue'

export default {
    components: {
        ReprieveLose
    },
    mounted() {
        this.userdata();
        this.get_area_options();
        this.get_level_options();
        this.get_cust_options();
    },
    data() {
        return {
            clientName_select: "",
            clientAreaId_select: undefined,
            clientLevelId_select: undefined,
            clientContentment_select: undefined,
            clientCredit_select: undefined,
            clientCustId_select: undefined,

            currentClientCode: "",

            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
            iconClasses: ['icon-rate-face-1', 'icon-rate-face-2', 'icon-rate-face-3'],// 等同于 { 2: 'icon-rate-face-1', 4: { value: 'icon-rate-face-2', excluded: true }, 5: 'icon-rate-face-3' }
            area_options: [],
            level_options: [],
            cust_options: [],
            Contentment_options: [
                { value: 1, label: '☆' },
                { value: 2, label: '☆☆' },
                { value: 3, label: '☆☆☆' },
                { value: 4, label: '☆☆☆☆' },
                { value: 5, label: '☆☆☆☆☆' }
            ],
            credit_options: [
                { value: 1, label: '😞' },
                { value: 2, label: '😕' },
                { value: 3, label: '😐' },
                { value: 4, label: '😊' },
                { value: 5, label: '😃' }
            ],
            state_options: [
                { value: 1, label: "正常" },
                { value: 2, label: "暂缓流失" },
                { value: 3, label: "已流失" },
            ],

            //用户信息详情
            dialogFormVisible1: false,
            form1: {
                countdown: undefined
            },
            total: 1000,
            // 分页
            pagesize: 5, //每页多少数据
            currentPage: 1, //默认当前页为第一页
            tableData: [],

            //暂缓流失
            deleteDialogVisible: false,
            editDialogVisible: false,
            total4: 1000,
            pagesize4: 2, //每页多少数据
            currentPage4: 1, //默认当前页为第一页
            form4: [],
            //当前是添加 true表示添加，false表示修改
            isAdding2: true,
            //设置成只能通过“暂缓流失”按钮点击才能进入暂缓流失界面
            isLosing: true,
            //确认流失输入框
            editDialogVisible2: false,
            lostReasonInput: "",
            //流失原因显示框
            editDialogVisible3: false,
            lostReasonShow: "",
            currentClientLostTime: "",
            tableData4: [],
        };
    },

    methods: {
        lostMethod(code) {
            this.currentClientCode = code;
            this.dialogFormVisible1 = true;
            this.getLost(this.currentClientCode);
            this.$forceUpdate();
        },
        handleBeforeClose(done) {
            this.dialogFormVisible1 = false;
            this.userdata()
        },
        //this.jump("/404");
        jump(path) {
            this.$router.push(path);
        },
        //清空搜索输入框
        clearCondition() {
            this.currentPage = 1;
            this.clientName_select = '';
            this.clientAreaId_select = undefined;
            this.clientLevelId_select = undefined;
            this.clientContentment_select = undefined;
            this.clientCredit_select = undefined;
            this.clientCustId_select = undefined;
            this.userdata();
            this.get_area_options();
            this.get_level_options();
            this.get_cust_options();
        },
        // 获取第一页表格数据
        async userdata() {
            const result = await this.$http.get(
                `/clientInfo/conditionSelect?currentPage=${1}&size=${5}&clientName=${''}&clientAreaId=${0}&clientLevelId=${0}&clientContentment=${0}&clientCredit=${0}&clientCustId=${0}&clientState=${2}`, {
            }
            );
            console.log(result)
            if (result.status === 200) {
                this.tableData = result.data.data;
                this.total = Number(result.data.total);
                this.$forceUpdate();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log(val);
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            if (this.username_select == "" && this.email_select == "") {
                const result = await this.$http.get(
                    `/clientInfo/conditionSelect?currentPage=${1}&size=${5}&clientName=${''}&clientAreaId=${0}&clientLevelId=${0}&clientContentment=${0}&clientCredit=${0}&clientCustId=${0}&clientState=${2}`, {
                }
                );
                if (result.status === 200) {
                    this.tableData = result.data.data;
                    this.total = Number(result.data.total);
                    this.$forceUpdate();
                }
            } else {
                var tmp_clientAreaId_select = this.clientAreaId_select;
                var tmp_clientLevelId_select = this.clientLevelId_select;
                var tmp_clientContentment_select = this.clientContentment_select;
                var tmp_clientCredit_select = this.clientCredit_select;
                var tmp_clientCustId_select = this.clientCustId_select;
                if (typeof this.clientAreaId_select === 'undefined' || this.clientAreaId_select === null || this.clientAreaId_select === '') {
                    tmp_clientAreaId_select = 0;
                }
                if (typeof this.clientLevelId_select === 'undefined' || this.clientLevelId_select === null || this.clientLevelId_select === '') {
                    tmp_clientLevelId_select = 0;
                }
                if (typeof this.clientContentment_select === 'undefined' || this.clientContentment_select === null || this.clientContentment_select === '') {
                    tmp_clientContentment_select = 0;
                }
                if (typeof this.clientCredit_select === 'undefined' || this.clientCredit_select === null || this.clientCredit_select === '') {
                    tmp_clientCredit_select = 0;
                }
                if (typeof this.clientCustId_select === 'undefined' || this.clientCustId_select === null || this.clientCustId_select === '') {
                    tmp_clientCustId_select = 0;
                }
                this.$http.get(`/clientInfo/conditionSelect?currentPage=${val}&size=${5}&clientName=${this.clientName_select}&clientAreaId=${tmp_clientAreaId_select}&clientLevelId=${tmp_clientLevelId_select}&clientContentment=${tmp_clientContentment_select}&clientCredit=${tmp_clientCredit_select}&clientCustId=${tmp_clientCustId_select}&clientState=${2}`, {})
                    .then(response => {
                        // 执行成功时的代码
                        this.tableData = response.data.data;
                        this.total = Number(response.data.total);
                        this.$forceUpdate();
                    })
                    .catch(error => {
                        // 请求失败时的代码
                        this.$message.error("查询失败");
                        console.error('请求失败：', error);
                    });
            }
        },
        //条件查询
        async conditionSelect_click() {
            this.currentPage = 1;
            var tmp_clientAreaId_select = this.clientAreaId_select;
            var tmp_clientLevelId_select = this.clientLevelId_select;
            var tmp_clientContentment_select = this.clientContentment_select;
            var tmp_clientCredit_select = this.clientCredit_select;
            var tmp_clientCustId_select = this.clientCustId_select;
            if (typeof this.clientAreaId_select === 'undefined' || this.clientAreaId_select === null || this.clientAreaId_select === '') {
                tmp_clientAreaId_select = 0;
            }
            if (typeof this.clientLevelId_select === 'undefined' || this.clientLevelId_select === null || this.clientLevelId_select === '') {
                tmp_clientLevelId_select = 0;
            }
            if (typeof this.clientContentment_select === 'undefined' || this.clientContentment_select === null || this.clientContentment_select === '') {
                tmp_clientContentment_select = 0;
            }
            if (typeof this.clientCredit_select === 'undefined' || this.clientCredit_select === null || this.clientCredit_select === '') {
                tmp_clientCredit_select = 0;
            }
            if (typeof this.clientCustId_select === 'undefined' || this.clientCustId_select === null || this.clientCustId_select === '') {
                tmp_clientCustId_select = 0;
            }
            const result = await this.$http.get(
                `/clientInfo/conditionSelect?currentPage=${1}&size=${5}&clientName=${this.clientName_select}&clientAreaId=${tmp_clientAreaId_select}&clientLevelId=${tmp_clientLevelId_select}&clientContentment=${tmp_clientContentment_select}&clientCredit=${tmp_clientCredit_select}&clientCustId=${tmp_clientCustId_select}&clientState=${2}`, {
                // headers: {
                //   Authorization: "Bearer " + JSON.parse(localStorage.getItem('userdata')).token
                // }
            }
            );
            if (result.status === 200) {
                this.tableData = result.data.data;
                this.total = Number(result.data.total);
                this.setCountDown();
                this.$forceUpdate();
                this.$message.success("查询成功");
            } else {
                this.$message.error("查询失败");
            }
        },

        //--------------------------------暂缓流失部分-----------------------------------------------
        //获取第一页表格数据
        async getLost(clientCode) {
            const result = await this.$http.get(
                `/lost/listBy?currentPage=${1}&size=${3}&clientCode=${clientCode}`, {}
            );
            console.log("-----" + result.data.data)
            if (result.status === 200) {
                this.tableData4 = result.data.data;
                this.total4 = Number(result.data.total);
                this.$forceUpdate();
            }
        },
        handleSizeChange4(val) {
            console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange4(val) {
            console.log(`当前页: ${val}`);
            const result = await this.$http.get(
                `/lost/listBy?currentPage=${val}&size=${3}&clientCode=${clientCode}`, {}
            );
            console.log(result)
            if (result.status === 200) {
                this.tableData4 = result.data.data;
                this.total4 = Number(result.data.total);
                this.$forceUpdate();
            } else {
                this.$message.error("查询失败");
            }
        },
        //修改
        editMeasure(index) {
            this.isAdding2 = false;
            this.editDialogVisible = true;
            this.form4 = this.tableData4[index];
        },
        async editdetail4() {
            const result = await this.$http.put("/lost/infoModify", this.form4, {
            });
            if (result.data.code === 200) {
                this.$message.success("修改成功！");
                await this.getLost(this.currentClientCode);
                this.$forceUpdate();
                this.editDialogVisible = false;
            } else if (result.data.code === 500) {
                this.$message.error(result.data.message);
                this.getLost(this.currentClientCode);
            } else {
                this.$message.error("修改失败!");
            }
        },
        //删除
        handleDelete4(index) {
            MessageBox.confirm('确认删除该措施吗？', '删除确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(async () => {
                    const result = await this.$http.delete("/lost/delete?clientReprieveLoseId=" + this.tableData4[index].clientReprieveLoseId, {
                    });
                    if (result.data.code == 200) {
                        this.$message.success(result.data.message);
                        this.userdata();
                        this.$forceUpdate();
                    } else {
                        this.$message.error(result.data.message);
                    }
                    this.getLost(this.currentClientCode);
                })
                .catch(() => {
                    // 用户点击了取消按钮，不执行任何操作
                    console.log('取消删除');
                });
        },
        // 添加信息
        addClick_button4() {
            this.isAdding2 = true;
            this.editDialogVisible = true;
            //清除数据
            this.form4 = {
                clientReprieveLoseId: "",
                reprieveStep: "",
                clientCode: "",
            }
        },
        async submitadd4() {
            this.form4.clientCode = this.currentClientCode;//设置当前暂缓流失措施对应的客户（即当前客户）
            console.log("add--" + this.form4)
            const result = await this.$http.post("/lost/add", this.form4, {
            });
            if (result.data.code === 200) {
                this.$message.success("添加成功!");
                this.userdata();
                this.$forceUpdate();
                this.editDialogVisible = false;
                //清除数据
                this.form4 = {
                    clientReprieveLoseId: "",
                    reprieveStep: "",
                    clientCode: "",
                }
                this.getLost(this.currentClientCode);
            } else {
                this.$message.error(result.data.message);
            }
        },
        lostMethod(code) {
            this.currentClientCode = code;
            this.isLosing = false;
            this.activeTab = "tab4";
            this.dialogFormVisible1 = true;
            this.getLost(this.currentClientCode);
            this.$forceUpdate();
        },
        //确认流失
        confirmLostClick_button4() {
            this.lostReasonInput = "";
            this.editDialogVisible2 = true;
        },
        async confirmLoss() {
            const result = await this.$http.put("/clientInfo/state?clientCode=" + this.currentClientCode + "&clientState=3" + "&clientLoseReason=" + this.lostReasonInput, {
            });
            if (result.data.code == 200) {
                this.$message.success(result.data.message);
                this.userdata();
                this.dialogFormVisible1 = false;
                this.userdata();
                this.$forceUpdate();
            } else {
                this.$message.error(result.data.message);
            }
        },
        //查看流失原因
        hasBeenLost(row) {
            this.editDialogVisible3 = true;
            this.currentClientCode = row.clientCode;
            this.lostReasonShow = row.clientLoseReason;
            this.currentClientLostTime = row.clientLoseDate;
        },
        //--------------------------------------------工具--------------------------------------------------------
        //获取area_options
        async get_area_options() {
            const response = await axios.get('/clientInfo/area_options');
            this.area_options = response.data;
            console.log(this.area_options)
        },
        //获取level_options
        async get_level_options() {
            const response = await axios.get('/clientInfo/level_options');
            this.level_options = response.data;
            console.log(this.level_options)
        },
        //获取cust_options
        async get_cust_options() {
            const response = await axios.get('/clientInfo/cust_options');
            this.cust_options = response.data;
            console.log(this.cust_options)
        },

        dateFormatter(value) {
            if (!value) return ''
            const date = new Date(value)
            const year = date.getFullYear()
            const month = date.getMonth() + 1
            const day = date.getDate()
            return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`
        },
        areaFormatter(row, column, cellValue, index) {
            const option = this.area_options.find(option => option.value === cellValue.toString())
            return option ? option.label : ''
        },
        levelFormatter(row, column, cellValue, index) {
            const option = this.level_options.find(option => option.value === cellValue.toString())
            return option ? option.label : ''
        },
        custFormatter(row, column, cellValue, index) {
            const option = this.cust_options.find(option => option.value === cellValue.toString())
            return option ? option.label : ''
        },
    }

}


</script>