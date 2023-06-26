<template>
  <el-card class="box-card" shadow="always">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="数据源名称" prop="sourceName">
        <el-input
          v-model="queryParams.sourceName"
          placeholder="请输入数据源名称"
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
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['metadata:tables:add']"
          >新增</el-button>
          <el-button
            type="warning"
            icon="el-icon-refresh"
            size="mini"
            @click="handleCacheRefresh"
          >刷新缓存</el-button>
        </el-button-group>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dataSourceList"
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
      <template v-for="(item, index) in tableColumns">
        <el-table-column
          v-if="item.show"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :formatter="item.formatter"
          align="center"
          show-overflow-tooltip
        />
      </template>

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
                @click="handleEdit(scope.row)"
                v-hasPermi="['metadata:tables:edit']"
              >编辑</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleDetail(scope.row)"
                v-hasPermi="['metadata:tables:detail']"
              >详情</el-button>

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['metadata:tables:remove']"
              >删除</el-button>
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
import { pageDataSource, delDataSource, refreshMetadata } from '@/api/metadata/datasource'

export default {
  name: 'DataSourceList',
  dicts: ['sys_data_status'],
  data() {
    return {
      tableHeight: document.body.offsetHeight - 310 + 'px',
      // 展示切换
      showOptions: {
        data: {},
        showList: true,
        showAdd: false,
        showEdit: false,
        showDetail: false
      },
      // 遮罩层
      loading: true,
      // 表格头
      tableColumns: [
        { prop: 'sourceName', label: '数据源名称', show: true },
        {
          prop: 'isSync',
          label: '同步状态',
          show: true,
          formatter: this.syncFormatter
        },
        {
          prop: 'status',
          label: '状态',
          show: true,
          formatter: this.statusFormatter
        },
        { prop: 'createTime', label: '创建时间', show: true }
      ],
      // 默认选择中表格头
      checkedTableColumns: [],
      tableSize: 'medium',
      // 状态数据字典
      statusOptions: [],
      // 数据源表格数据
      dataSourceList: [],
      // 总数据条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        sourceName: ''
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
      pageDataSource(this.queryParams).then(response => {
        this.loading = false
        if (response.code == '200') {
          const { data } = response
          this.dataSourceList = data.data
          this.total = data.total
          }

      }).catch((err) => {
          console.log(err.message)
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
        sourceName: ''
      }
      this.handleQuery()
    },
    /** 刷新列表 */
    handleRefresh() {
      this.getList()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.showOptions.data = {}
      this.showOptions.showList = false
      this.showOptions.showAdd = true
      this.showOptions.showEdit = false
      this.showOptions.showDetail = false
      this.$emit('showCard', this.showOptions)
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.showOptions.data.id = row.id
      this.showOptions.showList = false
      this.showOptions.showAdd = false
      this.showOptions.showEdit = true
      this.showOptions.showDetail = false
      this.$emit('showCard', this.showOptions)
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.showOptions.data.id = row.id
      this.showOptions.showList = false
      this.showOptions.showAdd = false
      this.showOptions.showEdit = false
      this.showOptions.showDetail = true
      this.$emit('showCard', this.showOptions)
    },
    /** 刷新缓存 */
    handleCacheRefresh() {
      refreshMetadata().then(response => {
        if (response.code == '200') {
          this.$message.success('刷新缓存成功')
        } else {
          this.$message.error('刷新缓存失败')
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('选中数据将被永久删除, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDataSource(row.id).then(response => {
            this.$message.success('删除成功')
            this.getList()

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
    syncFormatter(row, column, cellValue, index) {
      if (cellValue === '0') {
        return <el-tag type='warning'>未同步</el-tag>
      } else if (cellValue === '1') {
        return <el-tag type='info'>同步中</el-tag>
      } else if (cellValue === '2') {
        return <el-tag type='success'>已同步</el-tag>
      }
    },
    statusFormatter(row, column, cellValue, index) {
      const dictLabel = this.selectDictLabel(this.dict.type.sys_data_status, cellValue)
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
