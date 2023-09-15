<template>
  <el-card class="box-card" shadow="always">
    <div slot="header" class="clearfix">
      <span>{{ title }}</span>
      <el-button-group style="float: right;">
        <el-button size="mini" icon="el-icon-s-data" round @click="handleCall">接口调用</el-button>
        <el-button size="mini" icon="el-icon-back" round @click="showCard">返回</el-button>
      </el-button-group>
    </div>
    <div class="body-wrapper">
      <el-form ref="form1" :model="form" label-width="80px" :disabled="true">
        <el-row>
          <el-col :span="8">
            <el-form-item label="API名称">
              <el-input v-model="form.apiName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="API版本">
              <el-input v-model="form.apiVersion" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="请求类型">
              <el-input v-model="form.reqMethod" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="返回格式">
              <el-input v-model="form.resType" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="调用路径">
              <el-input v-model="form.apiUrl" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="apiCode">
              <el-input v-model="form.apiCode" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider content-position="left">请求数据</el-divider>
      <el-row>
        <el-col :span="24">
          <el-tabs v-model="activeTabName" type="border-card">
            <el-tab-pane label="请求头" name="table0">
              <el-table :data="apiHeaderList" stripe border :max-height="300" style="width: 100%; margin: 15px 0;">
                <el-table-column label="序号" width="55" align="center">
                  <template slot-scope="scope">
                    <span>{{ scope.$index +1 }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="KEY" label="键" align="center" show-overflow-tooltip />
                <el-table-column prop="VALUE" label="值" align="center" show-overflow-tooltip />
                <el-table-column prop="DESCRIPTION" label="描述" align="center" show-overflow-tooltip />
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="请求参数" name="table1">
              <el-table :data="form.reqParams" stripe border :max-height="300" style="width: 100%; margin: 15px 0;">
                <el-table-column label="序号" width="55" align="center">
                  <template slot-scope="scope">
                    <span>{{ scope.$index +1 }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="paramName" label="参数名称" align="center" show-overflow-tooltip />
                <el-table-column prop="nullable" label="是否允许为空" align="center" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.nullable" true-label="1" false-label="0" disabled />
                  </template>
                </el-table-column>
                <el-table-column prop="paramComment" label="描述" align="center" show-overflow-tooltip />
                <el-table-column prop="paramType" label="参数类型" align="center" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.paramType" placeholder="请选择参数类型">
                      <el-option v-for="dict in dict.type.data_param_type" :key="dict.value" :label="dict.label"
                        :value="dict.value"></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column prop="whereType" label="操作符" align="center" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.whereType" placeholder="请选择操作符">
                      <el-option v-for="dict in dict.type.data_where_type" :key="dict.value" :label="dict.label"
                        :value="dict.value"></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column prop="exampleValue" label="开始值" align="center" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.startValue" placeholder="请输入开始值" :disabled="scope.row.whereType != '13'" />
                  </template>
                </el-table-column>
                <el-table-column prop="exampleValue" label="结束值" align="center" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.endValue" placeholder="请输入结束值" :disabled="scope.row.whereType != '13'" />
                  </template>
                </el-table-column>
                <el-table-column prop="paramValue" label="参数值" align="center"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.paramValue" placeholder="请输入参数值" :disabled="scope.row.whereType == '13'" />
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
      <el-divider content-position="left">返回数据</el-divider>
      <el-row>
        <el-col :span="24">
          <div v-if="apiExecuting">
            <el-table :data="callData.dataList" stripe border :max-height="200" style="width: 100%; margin: 15px 0;">
              <el-table-column label="序号" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.$index + 1 }}</span>
                </template>
              </el-table-column>
              <template v-for="(column, index) in callData.columnList">
                <el-table-column :key="index" :prop="column" :label="column" align="center" show-overflow-tooltip />
              </template>
            </el-table>
            <el-pagination :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
              :current-page.sync="callData.pageNum" :page-size.sync="callData.pageSize" :total="callData.dataTotal"
              @size-change="handleSizeChange" @current-change="handleCurrentChange" />
          </div>
          <div v-else>暂无数据</div>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script>
  import {
    getDataApiDetail
  } from '@/api/market/dataapi'
  import {
    getApiCall,
    postApiCall
  } from '@/api/market/apimapping'

  export default {
    name: 'DataApiExample',
    dicts: ['sys_api_status', 'sys_api_mode', 'sys_api_return', 'sys_api_limit', 'sys_api_config_type',
      'data_where_type', 'data_param_type'
    ],
    props: {
      data: {
        type: Object,
        default: function() {
          return {}
        }
      }
    },
    data() {
      return {
        title: '数据API调用',
        id: '',
        // 展示切换
        showOptions: {
          data: {},
          showList: true,
          showAdd: false,
          showEdit: false,
          showDetail: false,
          showExample: false
        },
        isbetween: false,
        isexampleValue: true,
        activeTabName: 'table0',
        form: {},
        apiHeader: {},
        apiHeaderList: [],
        // 操作符数据字典
        whereTypeOptions: [],
        // 参数类型数据字典
        paramTypeOptions: [],
        apiExecuting: false,
        callData: {
          dataList: [],
          columnList: [],
          pageNum: 1,
          pageSize: 20,
          dataTotal: 0
        }
      }
    },
    created() {
      console.log('id:' + this.data.id)
    },
    mounted() {
      this.getDataApi(this.data.id)
    },
    methods: {
      showCard() {
        this.$emit('showCard', this.showOptions)
      },
      /** 获取详情 */
      getDataApi: function(id) {
        getDataApiDetail(id).then(response => {
          if (response.code == 200) {
            console.log(response)
            const {
              data
            } = response
            this.form = data.data
            this.apiHeader = data.header
            this.apiHeaderList.push({
              'KEY': 'api_key',
              'VALUE': data.header.apiKey,
              'DESCRIPTION': ''
            })
            this.apiHeaderList.push({
              'KEY': 'secret_key',
              'VALUE': data.header.secretKey,
              'DESCRIPTION': ''
            })
          }
        })
      },
      handleSizeChange(val) {
        this.callData.pageNum = 1
        this.callData.pageSize = val
        this.handleCall()
      },
      handleCurrentChange(val) {
        this.callData.pageNum = val
        this.handleCall()
      },
      handleCall() {
        const url = 'services/' + this.form.apiVersion + this.form.apiUrl
        const header = {
          api_key: this.apiHeader.apiKey,
          secret_key: this.apiHeader.secretKey
        }
        const data = {}
        data.pageNum = this.callData.pageNum
        data.pageSize = this.callData.pageSize
        data.apiCode = this.form.apiCode
        this.form.reqParams.forEach(param => {
          this.$set(data, param.paramName, param.paramValue)
        })
        if (this.form.reqMethod === 'GET') {
          console.log(data)
          console.log(header)
          console.log(this.data.id)
          getApiCall(url, header, data).then(response => {
            if (response.code == 200) {
              const {
                data
              } = response
              const dataList = data.data || []
              let columnList = []
              if (dataList.length > 0) {
                columnList = Object.keys(dataList[0])
              }
              this.callData.dataList = dataList
              this.callData.columnList = columnList
              this.callData.dataTotal = data.total
              this.apiExecuting = true
            }
          })
        } else if (this.form.reqMethod === 'POST') {
          postApiCall(url, header, data).then(response => {
            if (response.code == 200) {
              const {
                data
              } = response
              const dataList = data.data || []
              let columnList = []
              if (dataList.length > 0) {
                columnList = Object.keys(dataList[0])
              }
              this.callData.dataList = dataList
              this.callData.columnList = columnList
              this.callData.dataTotal = data.total
              this.apiExecuting = true
            }
          })
        }
      },
      wheretypechange(data) {
        if (data == '13') {
          this.isbetween = true;
          this.isexampleValue = false;
        } else {
          this.isbetween = false;
          this.isexampleValue = true;
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-card ::v-deep .el-card__body {
    height: calc(100vh - 230px);
    overflow-y: auto;
  }
</style>
