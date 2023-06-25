<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="资源名称"
        style="width: 200px;"
        class="filter-item"
      />
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="fetchData"
      >搜索</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
        v-hasPermi="['datax:resource:add']"
      >添加</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border=""
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">{{ scope.$index+1 }}</template>
      </el-table-column>
      <el-table-column label="资源名称" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="服务器地址" align="center">
        <template slot-scope="scope">{{ scope.row.serverIp }}</template>
      </el-table-column>
      <el-table-column label="资源地址" align="center">
        <template slot-scope="scope">{{ scope.row.resource_address}}</template>
      </el-table-column>
      <el-table-column label="资源类型" width="200" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.data_base_type" :value="scope.row.type"/>
        </template>
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
                v-hasPermi="['datax:resource:edit']"
              >编辑</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDelete(scope.row)"
                v-hasPermi="['datax:resource:remove']"
              >删除</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-position"
                @click="handleVisit(scope.row)"
                v-hasPermi="['datax:resource:visit']"
              >访问</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="right"
        label-width="140px"
        style="width: 400px; margin-left:50px;"
      >
      <el-form-item label="资源类型" prop="datasourceGroup">
        <el-select v-model="temp.type" placeholder="资源类型" style="width: 40%">
          <el-option
            v-for="dict in dict.type.data_base_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="temp.name" placeholder="资源名称"/>
        </el-form-item>
        <el-form-item label="资源地址" prop="resourcePath">
          <el-input v-model="temp.resource_address" placeholder="资源地址"/>
        </el-form-item>
        <el-form-item label="服务器地址：" prop="serverIp">
          <span slot="label">服务器地址
            <el-popover placement="right" trigger="hover">
              <p>填写集群主服务的服务器地址（必填）</p>
              <i slot="reference" class="el-icon-info" />
            </el-popover>
          </span>
          <el-input v-model="temp.serverIp" placeholder="服务器地址"/>
        </el-form-item>
        <el-form-item  label="服务器用户名" prop="serverUser">
          <span slot="label">服务器用户名
            <el-popover placement="right" trigger="hover">
              <p>填写集群主服务的服务器用户名（必填）</p>
              <i slot="reference" class="el-icon-info" />
            </el-popover>
          </span>
          <el-input v-model="temp.serverUser" placeholder="服务器用户名"/>
        </el-form-item>
        <el-form-item  label="服务器密码" prop="serverPassword">
          <span slot="label">服务器密码
            <el-popover placement="right" trigger="hover">
              <p>填写集群主服务的服务器密码（必填）</p>
              <i slot="reference" class="el-icon-info" />
            </el-popover>
          </span>
          <el-input v-model="temp.serverPassword" placeholder="服务器密码" type="password"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as user from "@/api/datax/datax-user";
import waves from "@/directive/waves"; // waves directive
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

export default {
  name: "User",
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "gray",
        deleted: "danger"
      };
      return statusMap[status];
    }
  },
   dicts: ['data_base_type'],
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      listQuery: {
        current: 1,
        size: 10,
        name: undefined
      },
      roles: ["ROLE_USER", "ROLE_ADMIN"],
      dialogPluginVisible: false,
      pluginData: [],
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "Edit",
        create: "Create"
      },
      rules: {
        type: [
          { required: true, message: "type is required", trigger: "change" }
        ],
        name: [
          { required: true, message: "name is required", trigger: "change" }
        ],
        serverIp: [
          { required: true, message: "serverIp is required", trigger: "blur" }
        ],
        serverUser: [
          { required: true, message: "serverUser is required", trigger: "blur" }
        ],
        serverPassword: [
          { required: true, message: "serverPassword is required", trigger: "blur" }
        ]
      },
      fwqName: true,
      fwqPas: true,
      temp: {
        id: undefined,
        role: "",
        name: "",
        resource_address: "",
        serverIp: "",
        serverUser: "",
        serverPassword: "",
        type: ""
      },
      resetTemp() {
        this.temp = this.$options.data().temp;
      }
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    selectFile(type) {
      console.log(type)
      if (type === '2') {
        this.temp.serverIp = "本机服务器"
        this.fwqName = false
        this.fwqPas = false
        }
    },
    fetchData() {
      this.listLoading = true;
      user.getResourceList(this.listQuery).then(response => {
        const { content } = response;
        this.total = content.recordsTotal;
        this.list = content.data;
        this.listLoading = false;
      });
    },
    handleCreate() {
      this.resetTemp();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let data = {
            id: this.temp.id,
            name: this.temp.name,
            resource_address: this.temp.resource_address,
            serverIp: this.temp.serverIp,
            serverUser: this.temp.serverUser,
            serverPassword: this.temp.serverPassword,
            type: this.temp.type
          };
          user.resourceAdd(data).then(() => {
            this.fetchData();
            this.dialogFormVisible = false;
            this.$notify({
              title: "Success",
              message: "Created Successfully",
              type: "success",
              duration: 2000
            });
          });
        }
      });
    },
    handleVisit(row) {
      window.open(row.resource_address)
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row); // copy obj
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          const params = {
            id: this.temp.id,
            name: this.temp.name,
            resource_address: this.temp.resource_address,
            serverIp: this.temp.serverIp,
            serverUser: this.temp.serverUser,
            serverPassword: this.temp.serverPassword,
            type: this.temp.type
          };
          const tempData = Object.assign({}, params);
          user.resourceUpdate(tempData).then(() => {
            this.fetchData();
            this.dialogFormVisible = false;
            this.$notify({
              title: "Success",
              message: "Update Successfully",
              type: "success",
              duration: 2000
            });
          });
        }
      });
    },
    handleDelete(row) {
      user.resourceDelete(row.id).then(response => {
        this.fetchData();
        this.$notify({
          title: "Success",
          message: "Delete Successfully",
          type: "success",
          duration: 2000
        });
      });
    }
  }
};
</script>
