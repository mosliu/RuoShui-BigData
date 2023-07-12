<template>
  <div class="app-container">
    <div class="filter-container">
        <div v-loading="loading" class="fl-container">
          <el-form ref="queryform" :model="queryform" :inline="true">
            <el-form-item>
              <el-form-item>
                <el-input v-model="queryform.fileName" placeholder="文件名(模糊查询)" class="wl-input" @input="handleQuery()">
                  <el-button slot="append" type="primary" icon="el-icon-search" class="wl-search" @click="handleQuery()" />
                </el-input>
              </el-form-item>
              <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
            </el-form-item>
          </el-form>
          <el-table :data="list" :header-cell-style="{background:'#f4f4f5','text-align':'center'}" class="wl-table" border>
            <el-table-column prop="id" :show-overflow-tooltip="true" label="编号" min-width="50" width="80" align="center" />
            <el-table-column label="资源类型" width="200" align="center">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.data_base_type" :value="scope.row.resourceType"/>
              </template>
            </el-table-column>
             <el-table-column prop="resourceName" :show-overflow-tooltip="true" label="资源名称" align="center" />
            <el-table-column prop="fileName" :show-overflow-tooltip="true" label="文件名" align="center" />
            <el-table-column prop="downloadJarHttp" :show-overflow-tooltip="true" label="http地址"
              align="center" />
              <el-table-column prop="remarks" :show-overflow-tooltip="true" label="备注" align="center" />
            <el-table-column prop="createTimeStr" :show-overflow-tooltip="true" label="上传时间"  align="center" />
            <el-table-column prop="operate" label="操作" width="180" fixed="right" align="center">
              <template slot-scope="scope">
                <el-link type="primary" icon="el-icon-delete" @click.native="deleteFile(scope.row)">删除</el-link>
                <el-link type="primary" @click.native="doCopy(scope.row)">复制URL</el-link>
                <el-link type="primary" @click.native="doCopyFileName(scope.row)">复制文件名</el-link>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination v-if="pageshow" class="wl-pagination" background layout="total, sizes, prev, pager, next"
            :current-page="currentPage" :page-sizes="[10, 15, 20, 50, 100, 150, 200]" :page-size="pageSize" :total="count"
            @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>

        <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="30%" >
          <el-form ref="dataForm" :model="temp" label-position="right" label-width="20%" style="width: 600px; margin-left:50px;">
            <el-form-item label="选择目录资源" prop="resourceId">
              <el-select v-model="temp.resourceId" placeholder="选择目录资源" style="width: 50%" >
                  <el-option
                      v-for="dict in dataResourceList"
                      :key="dict.id"
                      :label="dict.name"
                      :value="dict.id"
                  ></el-option>
              </el-select>
            </el-form-item>
          <el-form-item label="备注" prop="remarks">
            <el-input v-model="temp.remarks" style="width: 50%"/>
          </el-form-item>
             <el-upload class="upload-demo" ref="upload" drag action="/prod-api/flink/uploadJar/upload" multiple
                :auto-upload="false" :with-credentials="true" :limit="1" :before-upload="handleBefore"
                :on-success="handleFilUploadSuccess" :on-remove="handleRemove" :data="temp"
                >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              </el-upload>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleUpload">确 定</el-button>
          </div>
        </el-dialog>
    </div>
  </div>
</template>

<script>
  import {
    queryUploadFile,
    deleteFile
  } from '@/api/flink/upload'
  import {getFileResource} from "@/api/datax/datax-user";

  export default {
    name: 'UploadManage',
    created() {
      this.getFileResource();
    },
     dicts: ['data_base_type'],
    data() {
      return {
        options: {
          target: '/prod-api/flink/uploadJar/upload',
          chunkSize: 1024 * 1024 * 1024,
          testChunks: false
        },
        dataResourceList: [],
        attrs: {
          // 接受的文件类型 根据实际需要
          accept: ['.JAR']
        },
        fileStatusText(status, response) {
          const statusTextMap = {
            success: '成功了',
            error: '出错了',
            uploading: '上传中',
            paused: '暂停中',
            waiting: '等待中'
          }
          if (status === 'success' || status === 'error') {
            if (response.code == 200) {
              return statusTextMap[status]
            } else {
              alert(response.message)
              return statusTextMap['error']
            }
          } else {
            return statusTextMap[status]
          }
        },
        loading: true,
        queryform: {
          fileName: ''
        },
        list: [],
        temp: {
          resourceId: '',
          remarks: ''
        },
        uploadData: {
          resourceId: ''
        },
        count: 0,
        pageSize: 10,
        currentPage: 1,
        pageshow: true,
        dialogFormVisible: false,
        dialogStatus: "",
        textMap: {
          update: "Edit",
          create: "Create"
        },
      }
    },
    mounted() {
      this.handleQuery()
    },
    methods: {
      getFileResource() {
        getFileResource().then(response => {
          console.log(response.content);
          this.dataResourceList = response.content;
          console.log(this.dataResourceList);
        });
      },
      handleCreate() {
        this.dialogStatus = "create";
        this.dialogFormVisible = true;
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
      },

      // el-upload相关方法
      handleRemove(file, fileList) {
        console.log("handleRemove方法清理文件")
        console.log(file, fileList);
      },
      //文件弹窗里面确定按钮
      submitUpload() {
        this.$refs.upload.submit();
      },
      // 文件上传成功时的函数
      handleFilUploadSuccess(res, file, fileList) {
        console.log(res)
        const statusTextMap = {
          success: '成功了',
          error: '出错了',
          uploading: '上传中',
          paused: '暂停中',
          waiting: '等待中'
        }
        if (res.success === true || res.success === false) {
          if (res.code == 200) {
            alert("文件上传成功")
            this.$tab.refreshPage();
            return statusTextMap[status]
          } else {
            alert(res.message)
            return statusTextMap['error']
          }
        } else {
          return statusTextMap[status]
        }
      },
      handleUpdate() {
        console.log("handleUpdate方法")
        this.dialogFormVisible = true;
      },
      // 弹窗里面确定按钮处理文件上传的函数
      handleUpload() {
        console.log("handleUpload方法")
        // console.log(res,file)
        this.submitUpload()
        this.dialogFormVisible = false
      },
      // 上传前的回调函数
      handleBefore(file) {
        this.uploadData.fileid = this.uploadData.resourceId;
      },
      // 文件上传按钮
      fileUpload(row) {
        this.uploadData = this.temp;
        //console.log(document.cookie)
        this.dialogFormVisible = true;
      },

      handleQuery(event) { // 查询
        this.pageshow = false
        this.queryUserList()
        this.$nextTick(() => {
          this.pageshow = true
        }) // 解决界面页码不更新问题
      },
      handleSizeChange(pageSize) { // 设置分页大小事件
        this.pageSize = pageSize
        this.handleQuery()
      },
      complete() {
        console.log('complete', arguments)
        this.queryUserList()
      },
      fileComplete() {
        this.queryUserList()
        console.log('file complete', arguments)
      },
      handleCurrentChange(pageno) { // 处理分页事件
        this.currentPage = pageno
        this.handleQuery()
      },
      queryUserList() {
        this.loading = true
        const fileName = this.queryform.fileName ? this.queryform.fileName.trim() : ''
        queryUploadFile(this.currentPage, this.pageSize, fileName).then(response => {
          this.loading = false
          const {
            code,
            success,
            message,
            data
          } = response
          if (code !== '200' || !success) {
            this.$message({
              type: 'error',
              message: (message || '请求数据异常！')
            })
            return
          }
          this.count = data.total
          this.list = data.data
          if (this.count > 0 && this.list.length === 0) { // 调整PageNo
            this.currentPage = Math.ceil(this.count / this.pageSize)
            this.queryUserList()
          }
        }).catch(error => {
          this.loading = false
          this.$message({
            type: 'error',
            message: '请求异常！'
          })
          console.log(error)
        })
      },
      doCopy(row) {
        const {
          id,
          fileName,
          downloadJarHttp
        } = row
        this.$copyText(downloadJarHttp).then(function(e) {
          alert('复制jar地址成功:' + downloadJarHttp)
        }, function(e) {
          alert('Can not copy')
          console.log(e)
        })
      },
      doCopyFileName(row) {
        const {
          id,
          fileName,
          downloadJarHttp
        } = row
        this.$copyText(fileName).then(function(e) {
          alert('复制jar文件名字成功:' + fileName)
        }, function(e) {
          alert('Can not copy')
          console.log(e)
        })
      },
      deleteFile(row) { // 删除
        const {
          id,
          fileName
        } = row
        this.$confirm(`确定要删除[${fileName}]吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          deleteFile(id).then(response => {
            this.loading = false
            const {
              code,
              success,
              message,
              data
            } = response
            if (code !== '200' || !success) {
              this.$message({
                type: 'error',
                message: (message || '请求数据异常！')
              })
              return
            }
            this.handleQuery()
            this.$tab.refreshPage()
            this.$message({
              type: 'success',
              message: `删除[${fileName}]成功！`
            })
          }).catch(error => {
            this.loading = false
            this.$message({
              type: 'error',
              message: '请求异常！'
            })
            console.log(error)
          })
        })
      }
    }
  }
</script>

<style scoped>
  .fl-container {
    margin: 20px;
  }

  .wl-pagination {
    margin-top: 5px;
  }

  .fl-container>>>.el-form-item {
    margin-bottom: 25px !important;
  }

  .wl-table>>>.el-link [class*=el-icon-]+span {
    margin-left: 1px;
  }

  .wl-table>>>.el-link {
    margin-right: 2px;
    margin-left: 2px;
  }

  .wl-title {
    font-size: 16px;
    font-weight: 600;
    cursor: default;
    padding-right: 2px;
  }
</style>
