<template>
    <div>
        <h3>客户编号{{ currentClientCode }}</h3>
        <el-card class="box-card" shadow="hover">
            <el-row>
                <el-button type="success">成功暂缓</el-button>
                <el-button type="danger" @click="confirmLostClick_button4">确认流失</el-button>
                <el-button type="primary" @click="addClick_button4">添加措施</el-button>
                <el-button type="info" @click="test">刷新</el-button>
            </el-row>
            <br>
            <!-- 措施列表 -->
            <el-row>
                <el-col :span="24" v-for="(item, index) in tableData4" :key="index">
                    <el-card class="measure-card">
                        <div slot="header" class="clearfix">
                            <span class="measure-number">措施编号{{ item.clientReprieveLoseId }}</span>
                            <el-button class="close-button" type="text" icon="el-icon-close"
                                @click="handleDelete4(index)"></el-button>
                        </div>
                        <el-tooltip effect="dark" :content="item.reprieveStep" placement="top">
                            <div class="measure-content" @dblclick="editMeasure(index)" v-text="item.reprieveStep"></div>
                        </el-tooltip>
                    </el-card>
                </el-col>
            </el-row>

            <!-- 分页 -->
            <div class="block" style="margin-top: 20px;">
                <el-pagination @size-change="handleSizeChange4" @current-change="handleCurrentChange4"
                    :current-page.sync="currentPage4" :page-size="pagesize4" layout="prev, pager, next, jumper,->,total"
                    :total="total4">
                </el-pagination>
            </div>

            <!-- 添加\修改对话框 -->
            <el-dialog :visible.sync="editDialogVisible" width="35%">
                <template #title>
                    <h3 style="font-weight: bold; font-size: 20px;">措施编号 {{ form4.clientReprieveLoseId }}</h3>
                </template>
                <el-input type="textarea" autosize :rows="20" v-model="form4.reprieveStep" placeholder="请输入内容"
                    style="height: 200px;"></el-input>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submitadd4" v-show="isAdding2">添 加</el-button>
                    <el-button type="primary" @click="editdetail4" v-show="!isAdding2">修 改</el-button>
                </span>
            </el-dialog>

            <!--确认流失对话框 -->
            <el-dialog :visible.sync="editDialogVisible2" width="35%">
                <template #title>
                    <h3 style="font-weight: bold; font-size: 20px;">客户编号 {{ currentClientCode }}</h3>
                </template>
                <h3>当前日期：{{ currentDate | formatDate }}</h3>
                <el-input type="textarea" autosize :rows="20" v-model="lostReasonInput" placeholder="请输入客户流失原因"
                    style="height: 200px;"></el-input>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="confirmLoss">确 认</el-button> </span>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import axios from "axios";
import { MessageBox } from 'element-ui';

export default {
    mounted() {
        this.tableData4 = [];
        this.getLost(this.currentClientCode);
        this.$forceUpdate();
        setInterval(() => {
            this.currentDate = new Date();
        }, 1000);
    },
    props: {
        currentClientCode: {
            type: String,
            require: true
        },
        dialogFormVisible1:{
            type:Boolean,
            require:true
        },
    },
    watch: {
        currentClientCode(newValue) {
            this.getLost(newValue);
        }
    },
    data() {
        return {
            currentDate: new Date(),
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
    filters: {
        formatDate(date) {
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hour = date.getHours().toString().padStart(2, '0');
            const minute = date.getMinutes().toString().padStart(2, '0');
            const second = date.getSeconds().toString().padStart(2, '0');
            return `${year}-${month}-${day}`;
        }
    },
    methods: {
        test() {
            this.getLost(this.currentClientCode)
        },
        //获取第一页表格数据
        async getLost(clientCode) {
            // alert(this.currentClientCode)
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
                        this.getLost(this.currentClientCode);
                        this.$forceUpdate();
                    } else {
                        this.$message.error(result.data.message);
                    }
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
                this.getLost(this.currentClientCode);
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
                this.editDialogVisible2 = false;
                this.dialogFormVisible1=false;
                this.getLost(this.currentClientCode);
                this.$forceUpdate();
            } else {
                this.$message.error(result.data.message);
            }
        },
        // //查看流失原因
        // hasBeenLost(row) {
        //     this.editDialogVisible3 = true;
        //     this.currentClientCode = row.clientCode;
        //     this.lostReasonShow = row.clientLoseReason;
        //     this.currentClientLostTime = row.clientLoseDate;
        // },
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


<style>
.red {
    color: red;
}

.green {
    color: green;
}

.measure-card {
    margin-bottom: 20px;
    border-radius: 8px;
}

.measure-content {
    cursor: pointer;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.measure-number {
    font-weight: bold;
    font-size: 18px
}

.close-button {
    float: right;
    font-size: 18px;
    /* 设置叉号大小 */
    color: red;
    /* 设置叉号颜色 */
    font-weight: 900;
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
}

.red-text {
    color: red;
}

.yellow-text {
    color: yellow;
}

.green-text {
    color: #8dcd93;
}
</style>
  