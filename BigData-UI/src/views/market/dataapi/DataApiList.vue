<template>
  <el-card class="box-card" shadow="always">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="API名称" prop="apiName">
        <el-input
          v-model="queryParams.apiName"
          placeholder="请输入API名称"
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
          >新增</el-button>
        </el-button-group>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dataApiList"
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

     <el-table-column label="名称" align="center">
       <template slot-scope="scope">{{ scope.row.apiName }}</template>
     </el-table-column>
     <el-table-column label="API版本" align="center">
       <template slot-scope="scope">{{ scope.row.apiVersion }}</template>
     </el-table-column>
     <el-table-column label="API路径" align="center">
       <template slot-scope="scope">{{ scope.row.apiUrl }}</template>
     </el-table-column>
     <el-table-column label="ApiKey" align="center">
       <template slot-scope="scope">{{ scope.row.apiCode }}</template>
     </el-table-column>
     <el-table-column label="请求类型" align="center">
       <template slot-scope="scope">{{ scope.row.reqMethod }}</template>
     </el-table-column>
     <el-table-column label="返回格式" align="center">
       <template slot-scope="scope">{{ scope.row.resType }}</template>
     </el-table-column>
     <el-table-column label="状态" align="center">
       <template slot-scope="scope">
         <dict-tag :options="dict.type.sys_api_status" :value="scope.row.status"/>
       </template>
     </el-table-column>
     <el-table-column label="创建时间" align="center">
       <template slot-scope="scope">{{ scope.row.createTime }}</template>
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
              icon="el-icon-edit-outline"
              @click="handleEdit(scope.row)"
            >修改</el-button>
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
            <el-button
              size="mini"
              type="text"
              icon="el-icon-copy-document"
              @click="handleCopy(scope.row)"
            >拷贝</el-button>
            <el-button
              v-if="scope.row.status !== '2'"
              size="mini"
              type="text"
              icon="el-icon-upload2"
              @click="handleRelease(scope.row)"
            >发布</el-button>
            <el-button
              v-if="scope.row.status === '2'"
              size="mini"
              type="text"
              icon="el-icon-download"
              @click="handleCancel(scope.row)"
            >注销</el-button>
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
import { pageDataApi, delDataApi, copyDataApi, releaseDataApi, cancelDataApi } from '@/api/market/dataapi'

export default {
  name: 'DataApiList',
   dicts: ['sys_api_status'],
  data() {
    return {
      tableHeight: document.body.offsetHeight - 310 + 'px',
      // 展示切换
      showOptions: {
        data: {},
        showList: true,
        showAdd: false,
        showEdit: false,
        showDetail: false,
        showExample: false
      },
      // 遮罩层
      loading: true,

      tableSize: 'medium',
      // 状态数据字典
      statusOptions: [],
      // 数据集表格数据
      dataApiList: [],
      // 总数据条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        apiName: ''
      }
    }
  },
  created() {
    this.getDicts('data_api_status').then(response => {
      if (response.code == 200) {
        this.statusOptions = response.data
      }
    })
    this.getList()
  },
  mounted() {

  },
  methods: {
    /** 查询数据Api列表 */
    getList() {
      this.loading = true
      pageDataApi(this.queryParams).then(response => {
        this.loading = false
        if (response.code == 200) {
          console.log(response)
          const { data } = response
          this.dataApiList = data.data
          this.total = data.total
        }
      })
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
    /** 新增按钮操作 */
    handleAdd() {
      this.showOptions.data = {}
      this.showOptions.showList = false
      this.showOptions.showAdd = true
      this.showOptions.showEdit = false
      this.showOptions.showDetail = false
      this.showOptions.showExample = false
      this.$emit('showCard', this.showOptions)
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.showOptions.data.id = row.id
      this.showOptions.showList = false
      this.showOptions.showAdd = false
      this.showOptions.showEdit = true
      this.showOptions.showDetail = false
      this.showOptions.showExample = false
      this.$emit('showCard', this.showOptions)
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.showOptions.data.id = row.id
      this.showOptions.showList = false
      this.showOptions.showAdd = false
      this.showOptions.showEdit = false
      this.showOptions.showDetail = true
      this.showOptions.showExample = false
      this.$emit('showCard', this.showOptions)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('选中数据将被永久删除, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDataApi(row.id).then(response => {
          if (response.code == 200) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      }).catch(() => {
      })
    },
    /** 接口拷贝 */
    handleCopy(row) {
      this.$confirm('确认拷贝当前接口, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        copyDataApi(row.id).then(response => {
          if (response.code == 200) {
            this.$message.success('拷贝成功')
            this.getList()
          }
        })
      }).catch(() => {
      })
    },
    /** 接口发布 */
    handleRelease(row) {
      releaseDataApi(row.id).then(response => {
        if (response.code == 200) {
          this.$message.success('接口发布成功')
          this.getList()
        }
      })
    },
    /** 接口注销 */
    handleCancel(row) {
      cancelDataApi(row.id).then(response => {
        if (response.code == 200) {
          this.$message.success('接口注销成功')
          this.getList()
        }
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
        return <el-tag>{dictLabel}</el-tag>
      } else if (cellValue === '2') {
        return <el-tag type='success'>{dictLabel}</el-tag>
      } else if (cellValue === '3') {
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
  height: calc(100vh - 170px);
}
</style>
