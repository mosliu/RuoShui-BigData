<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.datasourceName"
        clearable
        placeholder="数据源名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">
        搜索
      </el-button>
      <el-button v-hasPermi="['datax:Datasource:add']" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-circle-plus" @click="handleCreate">
        添加
      </el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <!-- <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">{{ scope.$index }}</template>
      </el-table-column> -->
      <el-table-column label="数据源" width="200" align="center">
        <template slot-scope="scope">{{ scope.row.datasource }}</template>
      </el-table-column>
      <el-table-column label="数据源名称" width="150" align="center">
        <template slot-scope="scope">{{ scope.row.datasourceName }}</template>
      </el-table-column>
      <el-table-column label="数据源分组" width="200" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.source_group" :value="scope.row.datasourceGroup"/>
        </template>
      </el-table-column>
      <!--<el-table-column label="用户名" width="150" align="center">
        <template slot-scope="scope">{{ scope.row.jdbcUsername ? scope.row.jdbcUsername:'-' }}</template>
      </el-table-column>-->
      <el-table-column label="jdbc连接串" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">{{ scope.row.jdbcUrl ? scope.row.jdbcUrl:'-' }}</template>
      </el-table-column>
      <!-- <el-table-column label="jdbc驱动类" width="200" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">{{ scope.row.jdbcDriverClass ? scope.row.jdbcDriverClass:'-' }}</template>
      </el-table-column>-->
      <el-table-column label="ZK地址" width="200" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">{{ scope.row.zkAdress ? scope.row.zkAdress:'-' }}</template>
      </el-table-column>
      <el-table-column label="数据库名" width="200" align="center" :show-overflow-tooltip="true">-->
        <template slot-scope="scope">{{ scope.row.databaseName ? scope.row.databaseName:'-' }}</template>-->
      </el-table-column>
      <el-table-column label="备注" width="150" align="center">
        <template slot-scope="scope">{{ scope.row.comments }}</template>
      </el-table-column>
      <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
          <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['datax:Datasource:edit']"
              >编辑</el-button>

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['datax:Datasource:remove']"
              >删除</el-button>
            </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.current"
      :limit.sync="listQuery.size"
      @pagination="fetchData"
    />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="800px">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px">
        <el-form-item label="数据源" prop="datasource">
          <el-select
            v-model="temp.datasource"
            placeholder="数据源"
            style="width: 200px"
            @change="selectDataSource(temp.datasourceGroup,temp.datasource)"
          >
            <el-option v-for="item in dataSources" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据源名称" prop="datasourceName">
          <el-input v-model="temp.datasourceName" placeholder="数据源名称" style="width: 40%" />
        </el-form-item>
        <el-form-item label="数据源分组" prop="datasourceGroup">
          <el-select v-model="temp.datasourceGroup" placeholder="数据源分组" style="width: 40%" @change="myGroup(temp.datasourceGroup,temp.datasource)">
            <el-option
              v-for="dict in dict.type.source_group"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="jdbc" label="用户名">
          <el-input v-model="temp.jdbcUsername" placeholder="用户名" style="width: 40%" />
        </el-form-item>
        <el-form-item v-if="visible" v-show="jdbc" label="密码">
          <el-input v-model="temp.jdbcPassword" type="password" placeholder="密码" style="width: 40%">
            <i slot="suffix" title="显示密码" style="cursor:pointer" class="el-icon-view" @click="changePass('show')" />
          </el-input>
        </el-form-item>
        <el-form-item v-show="jdbc" v-else label="密码">
          <el-input v-model="temp.jdbcPassword" type="text" placeholder="密码" style="width: 40%">
            <i slot="suffix" title="隐藏密码" style="cursor:pointer" class="el-icon-check" @click="changePass('hide')" />
          </el-input>
        </el-form-item>
        <el-form-item v-if="jdbc" label="jdbc url" prop="jdbcUrl">
          <el-input
            v-model="temp.jdbcUrl"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            placeholder="jdbc url"
            style="width: 60%"
          />
        </el-form-item>
        <el-form-item v-if="mongodb" label="地址" prop="jdbcUrl">
          <el-input
            v-model="temp.jdbcUrl"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            placeholder="127.0.0.1:27017"
            style="width: 60%"
          />
        </el-form-item>
        <el-form-item v-if="jdbc" label="jdbc驱动类" prop="jdbcDriverClass">
          <el-input v-model="temp.jdbcDriverClass" placeholder="jdbc驱动类" style="width: 60%" />
        </el-form-item>
        <el-form-item v-if="orc" label="schema" prop="orcschema">
          <el-input v-model="temp.orcschema" placeholder="orcschema" style="width: 60%" />
        </el-form-item>
        <el-form-item v-if="hbase" label="ZK地址" prop="zkAdress">
          <el-input v-model="temp.zkAdress" placeholder="127.0.0.1:2181" style="width: 60%" />
        </el-form-item>
        <el-form-item v-if="mongodb" label="数据库名称" prop="databaseName">
          <el-input v-model="temp.databaseName" placeholder="数据库名称" style="width: 60%" />
        </el-form-item>
        <el-form-item label="注释">
          <el-input
            v-model="temp.comments"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="Please input"
            style="width: 60%"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
        <el-button type="primary" @click="testDataSource()">
          测试连接
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogPluginVisible" title="Reading statistics">
      <el-table :data="pluginData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import * as datasourceApi from '@/api/datax/datax-jdbcDatasource'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'

export default {
  name: 'JdbcDatasource',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
    dicts: ['source_group'],
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      listQuery: {
        current: 1,
        size: 10
      },
      pluginTypeOptions: ['reader', 'writer'],
      dialogPluginVisible: false,
      pluginData: [],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        datasourceName: [{ required: true, message: 'this is required', trigger: 'blur' }],
        jdbcUsername: [{ required: true, message: 'this is required', trigger: 'blur' }],
        jdbcPassword: [{ required: true, message: 'this is required', trigger: 'blur' }],
        jdbcUrl: [{ required: true, message: 'this is required', trigger: 'blur' }],
        jdbcDriverClass: [{ required: true, message: 'this is required', trigger: 'blur' }],
        datasource: [{ required: true, message: 'this is required', trigger: 'change' }],
        zkAdress: [{ required: true, message: 'this is required', trigger: 'blur' }],
        databaseName: [{ required: true, message: 'this is required', trigger: 'blur' }],
        orcschema: [{ required: true, message: 'this is required', trigger: 'blur' }]
      },
      temp: {
        id: undefined,
        datasourceName: '',
        datasourceGroup: '',
        jdbcUsername: '',
        jdbcPassword: '',
        jdbcUrl: '',
        jdbcDriverClass: '',
        comments: '',
        datasource: '',
        zkAdress: '',
        databaseName: '',
        orcschema: ''
      },
      visible: true,
      dataSources: [
        { value: 'mysql', label: 'mysql' },
        { value: 'oracle', label: 'oracle' },
        { value: 'postgresql', label: 'postgresql' },
        { value: 'sqlserver', label: 'sqlserver' },
        { value: 'hive', label: 'hive' },
        { value: 'hbase', label: 'hbase' },
        { value: 'mongodb', label: 'mongodb' },
        { value: 'clickhouse', label: 'clickhouse' }
      ],
      jdbc: true,
      hbase: false,
      mongodb: true,
      orc: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    selectDataSource(myGroup,datasource) {
     if (datasource === 'mysql') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:mysql://{host}:{port}/'
       }else{
         this.temp.jdbcUrl = 'jdbc:mysql://{host}:{port}/{database}'
       }
       this.temp.jdbcDriverClass = 'com.mysql.jdbc.Driver'
     } else if (datasource === 'oracle') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:oracle:thin:@//{host}:{port}/'
       }else{
         this.temp.jdbcUrl = 'jdbc:oracle:thin:@//{host}:{port}/{database}'
       }
       this.temp.jdbcDriverClass = 'oracle.jdbc.OracleDriver'
     } else if (datasource === 'postgresql') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:postgresql://{host}:{port}/'
       }else{
         this.temp.jdbcUrl = 'jdbc:postgresql://{host}:{port}/{database}'
       }
       this.temp.jdbcDriverClass = 'org.postgresql.Driver'
     } else if (datasource === 'sqlserver') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:sqlserver://{host}:{port};'
       }else{
         this.temp.jdbcUrl = 'jdbc:sqlserver://{host}:{port};DatabaseName={database}'
       }
       this.temp.jdbcDriverClass = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
     } else if (datasource === 'clickhouse') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:clickhouse://{host}:{port}/'
       }else{
         this.temp.jdbcUrl = 'jdbc:clickhouse://{host}:{port}/{database}'
       }
       this.temp.jdbcDriverClass = 'ru.yandex.clickhouse.ClickHouseDriver'
     } else if (datasource === 'hive') {
       if(myGroup === 'warehouse_building'){
         this.temp.jdbcUrl = 'jdbc:hive2://{host}:{port}/'
       }else{
         this.temp.jdbcUrl = 'jdbc:hive2://{host}:{port}/{database}'
       }
       this.temp.jdbcDriverClass = 'org.apache.hive.jdbc.HiveDriver'
       this.hbase = this.mongodb = false
       this.jdbc = true
     }
     this.getShowStrategy(datasource)
    },
    myGroup(myGroup,datasource){
      if (datasource === 'mysql') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:mysql://{host}:{port}/'
        }else{
          this.temp.jdbcUrl = 'jdbc:mysql://{host}:{port}/{database}'
        }
        this.temp.jdbcDriverClass = 'com.mysql.jdbc.Driver'
      } else if (datasource === 'oracle') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:oracle:thin:@//{host}:{port}/'
        }else{
          this.temp.jdbcUrl = 'jdbc:oracle:thin:@//{host}:{port}/{database}'
        }
        this.temp.jdbcDriverClass = 'oracle.jdbc.OracleDriver'
      } else if (datasource === 'postgresql') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:postgresql://{host}:{port}/'
        }else{
          this.temp.jdbcUrl = 'jdbc:postgresql://{host}:{port}/{database}'
        }
        this.temp.jdbcDriverClass = 'org.postgresql.Driver'
      } else if (datasource === 'sqlserver') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:sqlserver://{host}:{port};'
        }else{
          this.temp.jdbcUrl = 'jdbc:sqlserver://{host}:{port};DatabaseName={database}'
        }
        this.temp.jdbcDriverClass = 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
      } else if (datasource === 'clickhouse') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:clickhouse://{host}:{port}/'
        }else{
          this.temp.jdbcUrl = 'jdbc:clickhouse://{host}:{port}/{database}'
        }
        this.temp.jdbcDriverClass = 'ru.yandex.clickhouse.ClickHouseDriver'
      } else if (datasource === 'hive') {
        if(myGroup === 'warehouse_building'){
          this.temp.jdbcUrl = 'jdbc:hive2://{host}:{port}/'
        }else{
          this.temp.jdbcUrl = 'jdbc:hive2://{host}:{port}/{database}'
        }
        this.temp.jdbcDriverClass = 'org.apache.hive.jdbc.HiveDriver'
        this.hbase = this.mongodb = false
        this.jdbc = true
      }
      this.getShowStrategy(datasource)
    },
    fetchData() {
      this.listLoading = true
      datasourceApi.list(this.listQuery).then(response => {
        const { records } = response.data
        const { total } = response.data
        this.total = total
        this.list = records
        this.listLoading = false
      })
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        datasourceName: '',
        datasourceGroup: '',
        jdbcUsername: '',
        jdbcPassword: '',
        jdbcUrl: '',
        jdbcDriverClass: '',
        comments: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    cancel() {
      this.dialogFormVisible = false
      this.jdbc = true
      this.hbase = false
      this.mongodb = true
      this.orc = false
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          datasourceApi.created(this.temp).then(() => {
            this.fetchData()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    testDataSource() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          datasourceApi.test(this.temp).then(response => {
            if (response.data === false) {
              this.$notify({
                title: 'Fail',
                message: response.data.msg,
                type: 'fail',
                duration: 2000
              })
            } else {
              this.$notify({
                title: 'Success',
                message: 'Tested Successfully',
                type: 'success',
                duration: 2000
              })
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.getShowStrategy(row.datasource)
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          datasourceApi.updated(tempData).then(() => {
            this.fetchData()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    getShowStrategy(datasource) {
      if (datasource === 'hbase') {
        this.jdbc = this.mongodb = false
        this.hbase = true
      } else if (datasource === 'mongodb') {
        this.jdbc = this.hbase = false
        this.mongodb = true
        this.temp.jdbcUrl = 'mongodb://[username:password@]host1[:port1][,...hostN[:portN]]][/[database][?options]]'
      } else if (datasource === 'oracle') {
        this.orc = true
        this.mongodb = false
      } else {
        this.hbase = this.mongodb = false
        this.jdbc = true
      }
    },
    handleDelete(row) {
      console.log('删除')
      const idList = []
      idList.push(row.id)
      // 拼成 idList=xx
      // 多个比较麻烦，这里不处理
      datasourceApi.deleted({ idList: row.id }).then(response => {
        this.fetchData()
        this.$notify({
          title: 'Success',
          message: 'Delete Successfully',
          type: 'success',
          duration: 2000
        })
      })
      // const index = this.list.indexOf(row)
    },
    handleFetchPv(id) {
      datasourceApi.fetched(id).then(response => {
        this.pluginData = response
        this.dialogPvVisible = true
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    changePass(value) {
      this.visible = !(value === 'show')
    }
  }
}
</script>
