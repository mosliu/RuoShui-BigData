<template>
  <el-card class="box-card" shadow="always">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="接口名称" prop="apiName">
        <el-input
          v-model="queryParams.apiName"
          placeholder="请输入接口名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row type="flex" justify="space-between">
      <el-col :span="12">
        <el-button-group>
        </el-button-group>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="logList"
      border
      tooltip-effect="dark"
      :size="tableSize"
      :height="tableHeight"
      style="width: 100%;margin: 15px 0;"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="55" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
     <el-table-column label="接口名称" align="center">
       <template slot-scope="scope">{{ scope.row.apiName }}</template>
     </el-table-column>
     <el-table-column label="调用者ip" align="center">
        <template slot-scope="scope">{{ scope.row.callerIp }}</template>
      </el-table-column>
      <el-table-column label="调用数据量" align="center">
         <template slot-scope="scope">{{ scope.row.callerSize }}</template>
       </el-table-column>
       <el-table-column label="调用耗时" align="center">
          <template slot-scope="scope">{{ scope.row.time }}</template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.data_cipher_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="调用时间" align="center">
        <template slot-scope="scope">{{ scope.row.callerDate }}</template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
            >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      :current-page.sync="queryParams.pageNum"
      :page-size.sync="queryParams.pageSize"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </el-card>
</template>

<script>
import { pageApiLog, delApiLog } from '@/api/market/apilog'

export default {
  name: 'ApiLogList',
  dicts:['data_cipher_status'],
  data() {
    return {
      tableHeight: document.body.offsetHeight - 310 + 'px',
      // 展示切换
      showOptions: {
        data: {},
        showList: true,
        showDetail: false
      },
      // 遮罩层
      loading: true,

      tableSize: 'medium',
      // 日志表格数据
      logList: [],
      // 总数据条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        apiName: ''
      },
      // 状态数据字典
      statusOptions: []
    }
  },
  created() {
    this.getDicts('sys_normal_status').then(response => {
      if (response.code == 200) {
        this.statusOptions = response.data
      }
    })
    this.getList()
  },
  mounted() {
    this.initCols()
  },
  methods: {
    /** 查询日志列表 */
    getList() {
      this.loading = true
      pageApiLog(this.queryParams).then(response => {
        this.loading = false
        if (response.code == 200) {
          const { data } = response
          this.logList = data.data
          this.total = data.total
        }
      })
    },
    initCols() {
      this.checkedTableColumns = this.tableColumns.map(col => col.prop)
    },
    handleCheckedColsChange(val) {
      this.tableColumns.forEach(col => {
        if (!this.checkedTableColumns.includes(col.prop)) {
          col.show = false
        } else {
          col.show = true
        }
      })
    },
    handleCommand(command) {
      this.tableSize = command
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        apiName: ''
      }
      this.handleQuery()
    },
    /** 刷新列表 */
    handleRefresh() {
      this.getList()
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.showOptions.data.id = row.id
      this.showOptions.showList = false
      this.showOptions.showDetail = true
      this.$emit('showCard', this.showOptions)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('选中数据将被永久删除, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delApiLog(row.id).then(response => {
          if (response.code == 200) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      }).catch(() => {
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.queryParams.pageNum = val
      this.getList()
    },
    statusFormatter(row, column, cellValue, index) {
      const dictLabel = this.selectDictLabel(this.statusOptions, cellValue)
      if (cellValue === '1') {
        return <el-tag type='success'>{dictLabel}</el-tag>
      } else {
        return <el-tag type='warning'>{dictLabel}</el-tag>
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.right-toolbar {
  float: right;
}
.el-card ::v-deep .el-card__body {
  height: calc(105vh - 180px);
}
</style>
