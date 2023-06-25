<template>
  <el-card class="box-card" shadow="always">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="数据源" prop="sourceName">
        <el-input
          v-model="queryParams.sourceName"
          placeholder="请输入数据源"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据表" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入数据表"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对照字段" prop="columnName">
        <el-input
          v-model="queryParams.columnName"
          placeholder="请输入对照字段"
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
      :data="tableDataList"
      border
      tooltip-effect="dark"
      :size="tableSize"
      :height="tableHeight"
      style="width: 100%;margin: 15px 0;"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="55" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index +1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数据源" align="center">
          <template slot-scope="scope">{{ scope.row.sourceName }}</template>
      </el-table-column>
          <el-table-column label="数据表" align="center">
            <template slot-scope="scope">{{ scope.row.tableName }}</template>
          </el-table-column>
          <el-table-column label="对照字段" align="center">
            <template slot-scope="scope">{{ scope.row.columnName }}</template>
          </el-table-column>
          <el-table-column label="标准类别编码" align="center">
            <template slot-scope="scope">{{ scope.row.gbTypeCode }}</template>
          </el-table-column>
          <el-table-column label="标准类别名称" align="center">
            <template slot-scope="scope">{{ scope.row.gbTypeName }}</template>
          </el-table-column>

          <el-table-column label="已对照数量" align="center">
            <template slot-scope="scope">{{ scope.row.mappingCount }}</template>
          </el-table-column>
          <el-table-column label="未对照数量" align="center">
            <template slot-scope="scope">{{ scope.row.unMappingCount }}</template>
          </el-table-column>
          <el-table-column label="对照比例" align="center">
            <template slot-scope="scope">{{ Math.round(parseFloat(scope.row.mappingCount) / (parseFloat(scope.row.mappingCount) + parseFloat(scope.row.unMappingCount)) * 10000) / 100.00 + '%' }}</template>
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
import { contrastStat } from '@/api/standard/contrast'

export default {
  name: 'ContrastStatList',
  data() {
    return {
      tableHeight: document.body.offsetHeight - 310 + 'px',
      // 展示切换
      showOptions: {
        data: {},
        showList: true
      },
      // 遮罩层
      loading: true,
     
      tableSize: 'medium',
      // 表格数据
      tableDataList: [],
      // 总数据条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        sourceName: '',
        tableName: '',
        columnName: ''
      }
    }
  },
  created() {
    this.getList()
  },
  mounted() {
    this.initCols()
  },
  methods: {
    /** 查询数据源列表 */
    getList() {
      this.loading = true
      contrastStat(this.queryParams).then(response => {
        this.loading = false
        if (response.code == 200) {
          const { data } = response
          this.tableDataList = data.data
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
        sourceName: '',
        tableName: '',
        columnName: ''
      }
      this.handleQuery()
    },
    /** 刷新列表 */
    handleRefresh() {
      this.getList()
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
    mappingFormatter(row, column, cellValue, index) {
      return Math.round(parseFloat(row.mappingCount) / (parseFloat(row.mappingCount) + parseFloat(row.unMappingCount)) * 10000) / 100.00 + '%'
    }
  }
}
</script>

<style lang="scss" scoped>
.right-toolbar {
  float: right;
}
.el-card ::v-deep .el-card__body {
  height: calc(100vh - 170px);
}
</style>
