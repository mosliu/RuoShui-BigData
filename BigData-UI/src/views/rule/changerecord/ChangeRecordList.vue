<template>
  <el-card class="box-card" shadow="always">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="字段名称" prop="fieldName">
        <el-input
          v-model="queryParams.fieldName"
          placeholder="请输入字段名称"
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


    <el-table
      v-loading="loading"
      :data="changeRecordList"
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
      <el-table-column label="数据库表" align="center">
         <template slot-scope="scope">{{ scope.row.tableName }}</template>
      </el-table-column>
      <el-table-column label="变更字段" align="center">
         <template slot-scope="scope"> {{fieldNameFormatter(scope.row.fieldName) }}</template>
      </el-table-column>
      <el-table-column label="原来的值" align="center">
         <template slot-scope="scope">{{ scope.row.fieldOldValue }}</template>
      </el-table-column>
      <el-table-column label="最新的值" align="center">
         <template slot-scope="scope">{{ scope.row.fieldNewValue }}</template>
      </el-table-column>
      <el-table-column label="版本号" align="center">
         <template slot-scope="scope">{{ scope.row.version }}</template>
      </el-table-column>
      <el-table-column label="状态" align="center">
         <template slot-scope="scope">
           <dict-tag :options="dict.type.sys_data_status" :value="scope.row.status"/>
         </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{ scope.row.createTime}}</template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-popover
            placement="left"
            trigger="click"
          >
            <el-button
              v-hasPerm="['metadata:changerecord:edit']"
              size="mini"
              type="text"
              icon="el-icon-edit-outline"
              @click="handleEdit(scope.row)"
            >修改</el-button>
            <el-button
              v-hasPerm="['metadata:changerecord:detail']"
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleDetail(scope.row)"
            >详情</el-button>
            <el-button
              v-hasPerm="['metadata:changerecord:remove']"
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
            >删除</el-button>
            <el-button slot="reference">操作</el-button>
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
import { pageChangeRecord, delChangeRecord } from '@/api/metadata/changerecord'

export default {
  name: 'ChangeRecordList',
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

      // 默认选择中表格头
      checkedTableColumns: [],
      tableSize: 'medium',
      // 状态数据字典
      statusOptions: [],
      // 表格数据
      changeRecordList: [],
      // 总数据条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        fieldName: ''
      },
      dicts: new Map([
        ['columnName', '字段名称'],
        ['columnComment', '字段注释'],
        ['dataDefault', '数据默认值'],
        ['columnKey', '是否主键'],
        ['columnNullable', '是否允许为空'],
        ['dataType', '数据类型'],
        ['dataLength', '数据长度'],
        ['dataPrecision', '数据精度'],
        ['dataScale', '数据小数位']
      ])
    }
  },
  created() {
    this.getDicts('sys_common_status').then(response => {
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
    /** 查询数据源列表 */
    getList() {
      this.loading = true
      pageChangeRecord(this.queryParams).then(response => {
        this.loading = false
        if (response.code == 200) {
          const { data } = response
          this.changeRecordList = data.data
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
        fieldName: ''
      }
      this.handleQuery()
    },
    /** 刷新列表 */
    handleRefresh() {
      this.getList()
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
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('选中数据将被永久删除, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delChangeRecord(row.id).then(response => {
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
    },
    fieldNameFormatter(cellValue) {
      return this.dicts.get(cellValue)
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
