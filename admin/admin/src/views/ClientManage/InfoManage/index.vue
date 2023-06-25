
<!--用户列表-->
<template>
  <div>
    <el-card class="box-card" style="width: 100%">
      <el-breadcrumb style="margin-bottom:15px;" separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/' }">客户管理</el-breadcrumb-item>
        <el-breadcrumb-item>客户信息管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-divider></el-divider>
      <div style="display: flex">
        <div style="padding: 10px 0;">
          <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入客户名" style="width: 160px;margin:5px;"
            v-model="clientName_select"></el-input>
          <!-- <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入客户所在地区" style="width: 200px;margin:5px;" v-model="clientAreaId_select"></el-input>
          <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入客户等级" style="width: 200px;margin:5px;" v-model="clientLevelId_select"></el-input>
          <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入客户信用度" style="width: 200px;margin:5px;" v-model="clientCredit_select"></el-input>
          <el-input class="ml-5" suffix-icon="el-icon-search" placeholder="请输入负责经理id" style="width: 200px;margin:5px;" v-model="clientCustId_select"></el-input> -->
          <el-select v-model="clientAreaId_select" filterable placeholder="请选择客户所在地区" style="width: 150px;margin:5px;">
            <el-option v-for="item in area_options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>

          <el-select v-model="clientLevelId_select" filterable placeholder="请选择客户等级" style="width: 150px;margin:5px;">
            <el-option v-for="item in level_options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>


          <el-select v-model="clientContentment_select" filterable placeholder="请选择客户满意度"
            style="width: 160px;margin:5px;">
            <el-option v-for="item in Contentment_options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>

          <el-select v-model="clientCredit_select" filterable placeholder="请选择客户信用度" style="width: 160px;margin:5px;">
            <el-option v-for="item in credit_options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>

          <el-select v-model="clientCustId_select" filterable placeholder="请选择负责经理" style="width: 150px;margin:5px;">
            <el-option v-for="item in cust_options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>


          <el-button class="ml-5" type="primary" @click="conditionSelect_click">搜索</el-button>
          <el-button class="ml-5" type="danger" @click="clearCondition">清空</el-button>
          <!-- TODO: 导出功能尚未实现 -->
          <el-button class="ml-5" type="success" @click="setCountDown">导出</el-button>
        </div>
      </div>
      <div style="display: flex">
        <el-select v-model="clientState_select" filterable placeholder="请选择客户状态" style="width: 160px;margin:5px;">
          <el-option v-for="item in state_options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </div>
      <br>
      <!-- 编辑用户信息 -->
      <el-dialog title="客户详细信息" :visible.sync="dialogFormVisible1" width="50%" height="90%" append-to-body center
        :before-close="handleBeforeClose">
        <div>
          <el-tabs v-model="activeTab" @tab-click="handleTabClick">
            <!--------------------------------------------tab1 客户基本信息------------------------------------------------------------>
            <el-tab-pane label="客户基本信息" name="tab1">
              <el-card class="box-card" shadow="hover">
                <el-form :model="form1" style=":form1">
                  <el-row :gutter="20">
                    <el-col :span="12">

                      <div class="image-container">
                        <el-image src="https://www.qzqn8.com/wp-content/uploads/2020/02/2-9.jpg">
                        </el-image>
                      </div>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="注册资金（万元）" label-width="160px" label-position="left">
                        <el-input v-model="form1.clientBankroll"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="年营业额（万元）" label-width="160px" label-position="left">
                        <el-input v-model="form1.clientTurnoverYear"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="开户银行" label-width="160px" label-position="left">
                        <el-input v-model="form1.clientBank"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="法人" label-width="160px" label-position="left">
                        <el-input v-model="form1.clientCorporation"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="客户名称" label-width="100px" label-position="100px">
                        <el-input v-model="form1.clientName"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="客户所在地区" label-width="100px" label-position="left">
                        <el-select v-model="selectClientArea" filterable style="width: 150px;margin:5px;">
                          <el-option v-for="item in area_options" :key="item.value" :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="客户满意度" label-width="100px" label-position="left">
                        <div style="margin-top: 8px;">
                          <el-rate v-model="form1.clientContentment" :colors="colors">
                          </el-rate>
                        </div>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="客户地址" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientAddress"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="客户信用度" label-width="100px">
                        <div style="margin-top: 8px;">
                          <el-rate v-model="form1.clientCredit" :icon-classes="iconClasses"
                            void-icon-class="icon-rate-face-off" :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
                          </el-rate>
                        </div>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="所属客户经理" label-width="100px" label-position="left">
                        <el-select v-model="selectedClientCust" filterable style="width: 150px;margin:5px;">
                          <el-option v-for="item in cust_options" :key="item.value" :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="客户等级" label-width="100px" label-position="left">
                        <el-select v-model="selectClientLevel" filterable style="width: 150px;margin:5px;">
                          <el-option v-for="item in level_options" :key="item.value" :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="银行账号" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientAccounts"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="联系电话" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientTel"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="地税登记号" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientNativeTax"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="网址" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientNativeTax"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="国税登记号" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientCountryTax"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>

                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="传真" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientFax"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="邮政编码" label-width="100px" label-position="left">
                        <el-input v-model="form1.clientDakCode"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>

                <div style="display: flex">
                  <div style="flex: 1"></div>
                  <el-button type="primary" @click="editdetail()">修改</el-button>
                  <el-button @click="dialogFormVisible1 = false">取消</el-button>
                  <div style="flex: 1"></div>
                </div>
              </el-card>
            </el-tab-pane>
            <!------------------------------- tab2 客户联系人部分 -------------------------------------------------------------------->
            <el-tab-pane label="客户联系人" name="tab2">
              <el-card class="box-card" shadow="hover">
                <!--联系人信息表格-->
                <el-table :data="tableData2" border :row-style="{ height: '40px' }" :cell-style="{ padding: '0px' }"
                  max-height="1000" style="width: 100%" @selection-change="handleSelectionChange">
                  >
                  <el-table-column prop="clientLinkmanName" label="姓名" width="70">
                  </el-table-column>
                  <!-- <el-table-column prop="clientLinkmanSex" label="性别" width="50">
                  </el-table-column> -->
                  <el-table-column prop="clientLinkmanSex" label="性别" width="50">
                    <template slot-scope="scope">
                      {{ scope.row.clientLinkmanSex === 0 ? '男' : '女' }}
                    </template>
                  </el-table-column>
                  <el-table-column prop="clientLinkmanJob" label="职位" width="80">
                  </el-table-column>
                  <el-table-column prop="clientLinkmanTel" label="办公电话" width="120">
                  </el-table-column>
                  <el-table-column prop="clientLinkmanMobile" label="手机" width="120">
                  </el-table-column>
                  <el-table-column prop="remark" label="备注" width="80">
                  </el-table-column>
                  <el-table-column fixed="right" label="操作" width="120" header-align="center">
                    <template slot-scope="scope">
                      <!-- <el-button @click="" type="success" size="mini">编辑</el-button> -->
                      <el-button type="primary" icon="el-icon-edit" @click="handleClick2(scope.row)"></el-button>
                      <!-- <el-button @click="" type="danger" size="mini">删除</el-button> -->
                      <el-button type="danger" icon="el-icon-delete" @click="handleDelete2(scope.row)"></el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <!-- 分页 -->
                <div class="block" style="margin-top: 20px;">
                  <el-pagination @size-change="handleSizeChange2" @current-change="handleCurrentChange2"
                    :current-page.sync="currentPage2" :page-size="pagesize2" layout="prev, pager, next, jumper,->,total"
                    :total="total2">
                  </el-pagination>
                </div>

                <!-- 添加/编辑界面 -->
                <el-dialog title="编辑联系人" :visible.sync="dialogFormVisible2" width="30%">
                  <!-- <edit-contact-form ref="editContactForm" :itemId="currentItemId"></edit-contact-form> -->
                  <linkman-manage :form2.sync="form2">
                  </linkman-manage>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="submitadd2()" v-show="isAdding">添 加</el-button>
                    <el-button type="primary" @click="editdetail2()" v-show="!isAdding">修 改</el-button>
                  </div>
                </el-dialog>
                <br>
                <div style="float: right;">
                  <el-button type="primary" @click="addClick_button2">添 加 <i
                      class="el-icon-circle-plus-outline"></i></el-button>
                </div>
              </el-card>
            </el-tab-pane>
            <!------------------------------- tab3 查看历史订单部分 -------------------------------------------------------------------->
            <el-tab-pane label="查看历史订单" name="tab3">
              <el-card class="box-card" shadow="hover">
                <!--历史订单表格-->
                <el-table :data="tableData3" border :row-style="{ height: '40px' }" :cell-style="{ padding: '0px' }"
                  max-height="1000" style="width: 100%" @selection-change="handleSelectionChange">
                  >
                  <el-table-column prop="ordersId" label="订单编号" width="120">
                  </el-table-column>
                  <!-- <el-table-column prop="ordersDate" label="订单日期" width="120" :formatter="dateFormatter">
                  </el-table-column> -->

                  <el-table-column prop="ordersDate" label="订单状态" width="120">
                    <template slot-scope="scope">
                      <span>{{ dateFormatter(scope.row.ordersDate) }}</span>
                    </template>
                  </el-table-column>
                  <!-- <el-table-column prop="ordersState" label="订单状态" width="120">
                  </el-table-column> -->
                  <!-- <el-table-column prop="ordersState" label="订单状态" width="120">
                    <template slot-scope="scope">
                      {{ scope.row.ordersState === 1 ? '未回款' : '已回款' }}
                    </template>
                  </el-table-column> -->
                  <el-table-column prop="ordersState" label="订单状态" width="120">
                    <template slot-scope="scope">
                      <span :class="scope.row.ordersState === 1 ? 'red' : 'green'">
                        {{ scope.row.ordersState === 1 ? '未回款' : '已回款' }}
                      </span>
                    </template>
                  </el-table-column>

                  <el-table-column prop="ordersTotal" label="订单金额" width="120">
                  </el-table-column>
                  <el-table-column prop="remark" label="备注" width="169">
                  </el-table-column>
                </el-table>
                <!-- 分页 -->
                <div class="block" style="margin-top: 20px;">
                  <el-pagination @size-change="handleSizeChange3" @current-change="handleCurrentChange3"
                    :current-page.sync="currentPage3" :page-size="pagesize3" layout="prev, pager, next, jumper,->,total"
                    :total="total3">
                  </el-pagination>
                </div>
              </el-card>
            </el-tab-pane>
            <!------------------------------- tab4 暂缓流失部分-------------------------------------------------------------------->
            <el-tab-pane label="暂缓流失措施" name="tab4" :disabled="isLosing">
              <el-card class="box-card" shadow="hover">
                <el-row>
                  <el-button type="success">成功暂缓</el-button>
                  <el-button type="danger" @click="confirmLostClick_button4">确认流失</el-button>
                  <el-button type="primary" @click="addClick_button4">添加措施</el-button>
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
                  <el-input type="textarea" autosize :rows="20" v-model="lostReasonInput" placeholder="请输入客户流失原因"
                    style="height: 200px;"></el-input>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="confirmLoss">确 认</el-button> </span>
                </el-dialog>

              </el-card>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-dialog>


      <!--      用户信息表格-->
      <el-table :data="tableData" border :row-style="{ height: '50px' }" :cell-style="{ padding: '0px' }"
        max-height="1000" style="width: 100%" @selection-change="handleSelectionChange">
        >
        <el-table-column prop="clientCode" label="客户编号" width="150">
        </el-table-column>

        <el-table-column prop="clientName" label="客户名称" width="130">
        </el-table-column>

        <!-- <el-table-column prop="clientAreaId" label="所在地区" width="100">
        </el-table-column> -->
        <el-table-column prop="clientAreaId" label="所在地区" width="100" :formatter="areaFormatter">
        </el-table-column>

        <el-table-column prop="clientLevelId" label="客户等级" width="120" :formatter="levelFormatter">
        </el-table-column>

        <!-- <el-table-column label="客户等级" width="150">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.clientLevelId_select" :colors="colors" disabled>
            </el-rate>
          </template>
        </el-table-column> -->

        <el-table-column label="客户满意度" width="150">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.clientContentment" :colors="colors" disabled>
            </el-rate>
          </template>
        </el-table-column>


        <!-- <el-table-column prop="clientCredit" label="客户信用度" width="130">
        </el-table-column> -->

        <el-table-column label="客户信用度" width="150">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.clientCredit" :icon-classes="iconClasses" void-icon-class="icon-rate-face-off"
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']" disabled>
            </el-rate>
          </template>
        </el-table-column>
        <el-table-column prop="clientCustId" label="负责客户经理名称" width="120" :formatter="custFormatter">
        </el-table-column>

        <!-- <el-table-column label="暂缓流失" width="100" header-align="center">
          <template slot-scope="scope">
            <div style="display: flex">
              <div style="flex: 1"></div>
              <el-button @click="" type="danger" size="small">暂缓流失</el-button>
              <div style="flex: 1"></div>
            </div>
          </template>
        </el-table-column> -->
        <el-table-column prop="countdown" label="暂缓流失" width="100" header-align="center">
          <template slot-scope="{ row }">
            <div v-if="row.clientState == 1">
              <!-- 正常 -->
              <span :style="{ 'text-align': 'center', 'font-weight': 'bold', 'font-size': '16px', 'display': 'block' }"
                :class="{
                  'red-text': row.countdown >= 0 && row.countdown < 60,
                  'yellow-text': row.countdown >= 60 && row.countdown < 120,
                  'green-text': row.countdown >= 120 && row.countdown <= 180
                }">{{ row.countdown }}</span>
            </div>
            <el-button @click="lostMethod(row.clientCode)" type="danger" size="small"
              v-else-if="row.clientState == 2">暂缓流失</el-button>
            <el-button @click="hasBeenLost(row)" type="info" size="small" v-else-if="row.clientState == 3"
              style="margin-left: 6px;">已流失</el-button>

            <!-- 如果countdown>0，直接显示数值 -->
            <!-- <span v-if="row.countdown > 0" :style="{ 'text-align': 'center', 'font-weight': 'bold', 'font-size': '16px', 'display': 'block' }" :class="{
              'red-text': row.countdown >= 0 && row.countdown < 60,
              'yellow-text': row.countdown >= 60 && row.countdown < 120,
              'green-text': row.countdown >= 120 && row.countdown <= 180
            }">{{ row.countdown }}</span> -->
            <!-- 如果countdown<0，显示一个按钮 -->
            <!-- <div style="display: flex" v-else>
              <div style="flex: 1"></div>
              <el-button @click="lostMethod" type="danger" size="small">暂缓流失</el-button>
              <div style="flex: 1"></div>
            </div> -->
          </template>
        </el-table-column>

        <el-table-column label="操作" width="170" header-align="center">
          <template slot-scope="scope">
            <div style="display: flex">
              <el-button @click="handleClick(scope.row)" type="primary" size="small">详情</el-button>
              <el-button @click="handleClickHistory(scope.row)" type="danger" size="small">历史订单</el-button>
            </div>
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

      <!--流失原因对话框 -->
      <el-dialog :visible.sync="editDialogVisible3" width="35%">
        <template #title>
          <h3 style="font-weight: bold; font-size: 20px;">客户编号 {{ currentClientCode }}</h3>
        </template>
        <h4 style="text-align: left;">客户流失日期：{{ currentClientLostTime }}</h4>
        <h4 style="text-align: left;">客户流失原因：</h4>
        <el-input type="textarea" autosize :rows="20" v-model="lostReasonShow" style="height: 200px;" disabled>
        </el-input>
      </el-dialog>
    </el-card>
  </div>
</template>



<script>
import axios from "axios";
import LinkmanManage from "../LinkmanManage/index.vue"
import { MessageBox } from 'element-ui';

export default {
  components: {
    LinkmanManage
  },
  mounted() {
    this.userdata();
    this.get_area_options();
    this.get_level_options();
    this.get_cust_options();
  },
  computed: {
    selectedClientCust: {
      get() {
        return this.form1.clientCustId ? this.form1.clientCustId.toString() : '';
      },
      set(value) {
        this.form1.clientCustId = parseInt(value, 10);
      },
    },
    selectClientArea: {
      get() {
        return this.form1.clientAreaId ? this.form1.clientAreaId.toString() : '';
      },
      set(value) {
        this.form1.clientAreaId = parseInt(value, 10);
      },
    },
    selectClientLevel: {
      get() {
        return this.form1.clientLevelId ? this.form1.clientLevelId.toString() : '';
      },
      set(value) {
        this.form1.clientLevelId = parseInt(value, 10);
      },
    },
  },
  data() {
    return {
      clientName_select: "",
      clientAreaId_select: undefined,
      clientLevelId_select: undefined,
      clientContentment_select: undefined,
      clientCredit_select: undefined,
      clientCustId_select: undefined,
      clientState_select: undefined,

      activeTab: "tab1",
      currentClientCode: "",

      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
      iconClasses: ['icon-rate-face-1', 'icon-rate-face-2', 'icon-rate-face-3'],// 等同于 { 2: 'icon-rate-face-1', 4: { value: 'icon-rate-face-2', excluded: true }, 5: 'icon-rate-face-3' }
      // area_options : [
      //   { value: 1, label: '北京' },
      //   { value: 2, label: '华北' },
      //   { value: 3, label: '中南' },
      //   { value: 4, label: '东北' },
      //   { value: 5, label: '西部' }
      // ],
      area_options: [],
      level_options: [],
      cust_options: [],
      // level_options : [
      //   { value: 1, label: '普通客户' },
      //   { value: 2, label: '大客户' },
      //   { value: 3, label: '合作伙伴' },
      //   { value: 4, label: '战略合作伙伴' }
      // ],
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
      // cust_options: [{
      //   value: 1,
      //   label: '测试经理'
      // }],
      //-------------------------------------------------------------------------------------------------------
      //用户信息详情
      dialogFormVisible1: false,
      form1: {
        countdown: undefined
      },
      total: 1000,
      // 分页
      //currentPage: 1, //初始页
      pagesize: 5, //每页多少数据
      currentPage: 1, //默认当前页为第一页

      //客户联系人详情
      dialogFormVisible2: false,
      form2: {
      },
      //当前是添加 true表示添加，false表示修改
      isAdding: true,
      total2: 1000,
      pagesize2: 5, //每页多少数据
      currentPage2: 1, //默认当前页为第一页

      //历史订单
      total3: 1000,
      pagesize3: 5, //每页多少数据
      currentPage3: 1, //默认当前页为第一页

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
      //-------------------------------------------------------------------------------------------------------
      tableData: [],
      tableData2: [],
      tableData3: [],
      tableData4: [],
      testData: "2",
    };
  },

  methods: {
    handleTabClick(tab) {
      console.log('当前tab的name：', tab.name);
      if (tab.name == "tab2") {
        this.getLinkman(this.currentClientCode);
      } else if (tab.name == "tab3") {
        this.getOrders(this.currentClientCode);
      } else if (tab.name == "tab4") {
        this.getLost(this.currentClientCode);
      }
    },
    handleBeforeClose(done) {
      this.isLosing = true;
      this.dialogFormVisible1 = false;
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
      this.clientState_select=undefined;
      this.userdata();
      this.get_area_options();
      this.get_level_options();
      this.get_cust_options();
    },
    //设置最短时间
    async setCountDown() {
      for (let i = 0; i < this.tableData.length; i++) {
        const clientCode_ = this.tableData[i].clientCode;

        const response = await axios.get('/orders/countdown', {
          params: {
            clientCode: clientCode_
          }
        });
        var countdown_num = response.data;

        this.tableData[i] = Object.assign({}, this.tableData[i], { countdown: countdown_num })
      }
      this.$forceUpdate()
    },
    // 获取第一页表格数据
    async userdata() {

      const result = await this.$http.get(
        `/clientInfo/list?currentPage=${1}&size=${5}`, {
      }
      );
      console.log(result)
      if (result.status === 200) {
        this.tableData = result.data.data;
        this.setCountDown();
        this.total = Number(result.data.total);
        this.$forceUpdate();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    handleClick(row) {
      this.activeTab = "tab1";
      // 点击编辑获取信息
      this.dialogFormVisible1 = true;
      var tmp = Object.assign({}, row);
      this.form1 = tmp;
      this.currentClientCode = tmp.clientCode;
      if (row.countdown < 0) {
        this.isLosing = false;
      } else {
        this.isLosing = true;
      }
      console.log(this.form1);
    },
    handleClickHistory(row) {
      this.currentClientCode = row.clientCode;
      this.activeTab = "tab3";
      this.dialogFormVisible1 = true;
      this.getOrders(this.currentClientCode);
      this.$forceUpdate();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },

    async handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      if (this.username_select == "" && this.email_select == "") {
        const result = await this.$http.get(
          `/clientInfo/list?currentPage=${val}&size=${5}`, {
        }
        );
        if (result.status === 200) {
          this.tableData = result.data.data;
          this.total = Number(result.data.total);
          this.setCountDown();
          this.$forceUpdate();
        }
      } else {
        var tmp_clientAreaId_select = this.clientAreaId_select;
        var tmp_clientLevelId_select = this.clientLevelId_select;
        var tmp_clientContentment_select = this.clientContentment_select;
        var tmp_clientCredit_select = this.clientCredit_select;
        var tmp_clientCustId_select = this.clientCustId_select;
        var tmp_clientState_select=this.clientState_select;
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
        if (typeof this.clientState_select === 'undefined' || this.clientState_select === null || this.clientState_select === '') {
          tmp_clientState_select= 0;
        }
        this.$http.get(`/clientInfo/conditionSelect?currentPage=${val}&size=${5}&clientName=${this.clientName_select}&clientAreaId=${tmp_clientAreaId_select}&clientLevelId=${tmp_clientLevelId_select}&clientContentment=${tmp_clientContentment_select}&clientCredit=${tmp_clientCredit_select}&clientCustId=${tmp_clientCustId_select}&clientState=${tmp_clientState_select}`, {})
          .then(response => {
            // 执行成功时的代码
            this.tableData = response.data.data;
            this.setCountDown();
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
      var tmp_clientState_select=this.clientState_select;
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
      if (typeof this.clientState_select === 'undefined' || this.clientState_select === null || this.clientState_select === '') {
          tmp_clientState_select= 0;
      }
      const result = await this.$http.get(
        `/clientInfo/conditionSelect?currentPage=${1}&size=${5}&clientName=${this.clientName_select}&clientAreaId=${tmp_clientAreaId_select}&clientLevelId=${tmp_clientLevelId_select}&clientContentment=${tmp_clientContentment_select}&clientCredit=${tmp_clientCredit_select}&clientCustId=${tmp_clientCustId_select}&clientState=${tmp_clientState_select}`, {
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
    // 修改用户信息
    async editdetail() {
      const result = await this.$http.put("/clientInfo/infoModify", this.form1, {
        headers: {
          Authorization: "Bearer " + JSON.parse(localStorage.getItem('userdata')).token
        }
      });
      if (result.data.code === 200) {
        this.$message.success("修改成功！");
        await this.userdata();
        this.$forceUpdate();
        this.dialogFormVisible1 = false;
      } else if (result.data.code === 500) {
        this.$message.error(result.data.message);
        this.userdata();
      } else {
        this.$message.error("修改失败!");
      }
    },
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
    //--------------------------------客户联系人部分-----------------------------------------------
    //获取第一页表格数据
    async getLinkman(clientCode) {
      const result = await this.$http.get(
        `/linkman/list?currentPage=${1}&size=${5}&clientCode=${clientCode}`, {}
      );
      console.log(result)
      if (result.status === 200) {
        this.tableData2 = result.data.data;
        this.total2 = Number(result.data.total);
        this.$forceUpdate();
      } else {
        this.$message.error("查询失败");
      }
    },
    handleSizeChange2(val) {
      console.log(`每页 ${val} 条`);
    },
    async handleCurrentChange2(val) {
      console.log(`当前页: ${val}`);
      const result = await this.$http.get(
        `/linkman/list?currentPage=${val}&size=${5}&clientCode=${clientCode}`, {}
      );
      console.log(result)
      if (result.status === 200) {
        this.tableData2 = result.data.data;
        this.total2 = Number(result.data.total);
        this.$forceUpdate();
      } else {
        this.$message.error("查询失败");
      }
    },
    addClick_button2() {
      this.isAdding = true;
      this.dialogFormVisible2 = true;
    },
    handleClick2(row) {
      this.isAdding = false;
      // 点击编辑获取信息
      this.dialogFormVisible2 = true;
      var tmp = Object.assign({}, row);
      this.form2 = tmp;
      console.log(this.form2);
    },
    // 修改联系人信息
    async editdetail2() {
      this.isAdding = false;
      const result = await this.$http.put("/linkman/infoModify", this.form2, {
      });
      if (result.data.code === 200) {
        this.$message.success("修改成功！");
        await this.getLinkman(this.currentClientCode);
        this.$forceUpdate();
        this.dialogFormVisible2 = false;
      } else if (result.data.code === 500) {
        this.$message.error(result.data.message);
        this.getLinkman(this.currentClientCode);
      } else {
        this.$message.error("修改失败!");
      }
    },
    //删除联系人信息
    handleDelete2(linkman) {
      MessageBox.confirm('确认删除该联系人吗？', '删除确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          const result = await this.$http.delete("/linkman/delete?clientLinkmanId=" + linkman.clientLinkmanId, {
          });
          if (result.data.code == 200) {
            this.$message.success(result.data.message);
            this.userdata();
            this.$forceUpdate();
          } else {
            this.$message.error(result.data.message);
          }
          this.getLinkman(this.currentClientCode);
        })
        .catch(() => {
          // 用户点击了取消按钮，不执行任何操作
          console.log('取消删除');
        });
    },
    // 添加用户信息
    async submitadd2() {
      this.isAdding = true;
      this.form2.clientCode = this.currentClientCode;//设置当前客户联系人对应的客户（即当前客户）
      const result = await this.$http.post("/linkman/add", this.form2, {
      });
      console.log("---", result)
      if (result.data.code === 200) {
        this.$message.success("添加成功!");
        this.userdata();
        this.$forceUpdate();
        this.dialogFormVisible2 = false;
        //清除数据
        this.form2 = {
          clientLinkmanName: '',
          clientLinkmanSex: 0,
          clientLinkmanJob: '',
          clientLinkmanTel: '',
          clientLinkmanMobile: '',
          remark: '',
          clientCode: "",
        }
        this.getLinkman(this.currentClientCode);
      } else {
        this.$message.error(result.data.message);
      }
    },

    //--------------------------------历史订单部分-----------------------------------------------
    //获取第一页表格数据
    async getOrders(clientCode) {
      const result = await this.$http.get(
        `/orders/listBy?currentPage=${1}&size=${5}&ordersState=${0}&clientCode=${clientCode}`, {}
      );
      console.log(result)
      if (result.status === 200) {
        this.tableData3 = result.data.data;
        this.total3 = Number(result.data.total);
        this.$forceUpdate();
      }
    },
    handleSizeChange3(val) {
      console.log(`每页 ${val} 条`);
    },
    async handleCurrentChange3(val) {
      console.log(`当前页: ${val}`);
      const result = await this.$http.get(
        `/orders/listBy?currentPage=${val}&size=${5}&ordersState=${0}&clientCode=${clientCode}`, {}
      );
      console.log(result)
      if (result.status === 200) {
        this.tableData3 = result.data.data;
        this.total3 = Number(result.data.total);
        this.$forceUpdate();
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
    // 封装上传文件请求
    doupload() {
      return axios.defaults.baseURL + "/file/upload";
    },
    // 成功上传之后的回调函数
    handleAvatarSuccess(res) {
      this.form.avatar = res.obj
      console.log(res);
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type == "image/png";
      const isLt2M = file.size / 1024 / 1024 < 5;
      console.log(file);
      // 实例化一个阅读器对象
      var reader = new FileReader();
      // 读取文件的路径，没有返回值,结果在reader.result里
      reader.readAsDataURL(file);
      // 读取需要时间，读完后再修改图片路径
      var that = this;
      reader.onload = function () {
        //回显给上方图片中
        that.form.avatar = this.result;
      };
      if (
        file.type == "image/png" ||
        file.type == "image/jpg" ||
        file.type == "image/jpeg" ||
        file.type == "image/JPG"
      ) {
        // this.$message.error("上传头像图片格式不正确");
      } else {
        this.$message.error("上传头像图片格式不正确");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 5MB!");
      }
      return isLt2M;
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
    // 成功上传之后的回调函数
    handleAvatarSuccess1(res) {
      this.form1.avatar = res.obj
      console.log(res);
    },
    beforeAvatarUpload1(file) {
      // const isJPG = file.type == "image/png";
      const isLt2M = file.size / 1024 / 1024 < 5;
      console.log(file);
      // 实例化一个阅读器对象
      var reader = new FileReader();
      // 读取文件的路径，没有返回值,结果在reader.result里
      reader.readAsDataURL(file);
      // 读取需要时间，读完后再修改图片路径
      var that = this;
      reader.onload = function () {
        //回显给上方图片中
        that.form1.avatar = this.result;
      };
      if (
        file.type == "image/png" ||
        file.type == "image/jpg" ||
        file.type == "image/jpeg" ||
        file.type == "image/JPG"
      ) {
        // this.$message.error("上传头像图片格式不正确");
      } else {
        this.$message.error("上传头像图片格式不正确");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 5MB!");
      }
      return isLt2M;
    },
    async test() {
      const response = await axios.get('/orders/countdown', {
        params: {
          clientCode: 'KH202201001'
        }
      });

      var test = response.data;
      console.log("test=" + test)
    },
  },
};
</script>

<style lang="less" scoped>
.carid {
  display: none;
}

.demo-form-inline {
  display: flex;
  flex: left;
}

.el-form-item {
  margin-bottom: 20px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  /*border-radius: 100px;*/
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

// .avatar-uploader .el-upload:hover {
// //   border-color: #409eff;
// }
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 100px;
}

.upload .el-upload__input {
  display: none;
}

input[type="file"] {
  display: none !important;
}

.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  width: 200px;
  margin-left: 70px;
}

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
