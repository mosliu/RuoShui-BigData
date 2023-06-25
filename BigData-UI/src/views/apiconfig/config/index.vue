<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="API名称" prop="apiName">
        <el-input
          v-model="queryParams.apiName"
          placeholder="请输入API名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求方式" prop="requestMode">
        <el-select v-model="queryParams.requestMode" placeholder="请选择请求方式" clearable>
          <el-option
            v-for="dict in dict.type.sys_api_mode"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="IP限制" prop="ipRights">
        <el-select v-model="queryParams.ipRights" placeholder="IP限制" clearable>
          <el-option
            v-for="dict in dict.type.sys_api_id"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="到期时间" prop="expirationTime">
        <el-date-picker clearable
          v-model="queryParams.expirationTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择到期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="api状态" prop="apiState">
        <el-select v-model="queryParams.apiState" placeholder="请选择api状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_api_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['apiconfig:config:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['apiconfig:config:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['apiconfig:config:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['apiconfig:config:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
      <af-table-column type="selection" width="55" align="center" />
      <af-table-column label="API名称" align="center" prop="apiName" />
      <af-table-column label="数据源名称" align="center" prop="dataSourceName" />
      <af-table-column label="查询SQL" align="center" prop="querySql" width="140" show-overflow-tooltip/>
      <af-table-column label="JSON参数" align="center" prop="parameter" width="140" show-overflow-tooltip/>
      <af-table-column label="公钥" align="center" prop="publickey" width="140" show-overflow-tooltip/>
      <af-table-column label="口令" align="center" prop="token" width="140" show-overflow-tooltip />
      <af-table-column label="请求方式" align="center" prop="requestMode">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_api_mode" :value="scope.row.requestMode"/>
        </template>
      </af-table-column>
      <af-table-column label="IP限制" align="center" prop="ipRights">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_api_id" :value="scope.row.ipRights"/>
        </template>
      </af-table-column>
      <af-table-column label="到期时间" align="center" prop="expirationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expirationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </af-table-column>
      <af-table-column label="API状态" align="center" prop="apiState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_api_state" :value="scope.row.apiState"/>
        </template>
      </af-table-column>
      <af-table-column label="创建人" align="center" prop="createBy" />
      <af-table-column label="备注" align="center" prop="remark" />
      <af-table-column label="操作" align="center" class-name="small-padding fixed-width" width="140">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['apiconfig:config:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['apiconfig:config:remove']"
          >删除</el-button>
        </template>
      </af-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改数据API对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="API名称" prop="apiName">
          <el-input v-model="form.apiName" placeholder="请输入api名称" />
        </el-form-item>
        <el-form-item label="数据源" prop="dataSourceId" >
          <el-select v-model="form.dataSourceId" placeholder="请选择数据源">
            <el-option v-for="dict in dataSourceList" :key="dict.id" :label="dict.datasourceName" :value="dict.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查询SQL">
          <el-input v-model="form.querySql" type="textarea" placeholder="请输入SQL" hidden="600" />
        </el-form-item>
        <el-form-item label="JSON参数" prop="parameter">
          <el-input v-model="form.parameter" placeholder="请输入json参数" />
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMode">
          <el-select v-model="form.requestMode" placeholder="请选择请求方式">
            <el-option
              v-for="dict in dict.type.sys_api_mode"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="IP限制" prop="ipRights">
          <el-select v-model="form.ipRights" placeholder="IP限制" @change="selectIp(form.ipRights)">
            <el-option
              v-for="dict in dict.type.sys_api_id"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="ip" label="IP地址" prop="ipAddress">
         <el-input   v-model="form.ipAddress"  placeholder="请输入IP地址,多个用,隔开" hidden="600" />
        </el-form-item>
        <el-form-item label="到期时间" prop="expirationTime">
          <el-date-picker clearable
            v-model="form.expirationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择到期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="API状态" prop="apiState">
          <el-select v-model="form.apiState" placeholder="请选择api状态">
            <el-option
              v-for="dict in dict.type.sys_api_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listConfig, getConfig, delConfig, addConfig, updateConfig } from "@/api/apiconfig/config";
import { getdataSourceAll } from "@/api/rule/rule";
export default {
  name: "Config",
  dicts: ['sys_api_state', 'sys_api_mode', 'sys_api_id'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 数据API表格数据
      configList: [],
      dataSourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        apiName: null,
        requestMode: null,
        ipRights: null,
        expirationTime: null,
        apiState: null,
        createBy: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        apiName: [
          { required: true, message: "api名称不能为空", trigger: "blur" }
        ],
        dataSourceId: [
          { required: true, message: "数据源id不能为空", trigger: "change" }
        ],
        querySql: [
          { required: true, message: "查询sql不能为空", trigger: "blur" }
        ],
        requestMode: [
          { required: true, message: "请求方式不能为空", trigger: "change" }
        ],
        ipRights: [
          { required: true, message: "是否限制请求ip不能为空", trigger: "change" }
        ],
        expirationTime: [
          { required: true, message: "到期时间不能为空", trigger: "blur" }
        ],
        apiState: [
          { required: true, message: "api状态不能为空", trigger: "change" }
        ],
      },
      ip: false
    };
  },
  created() {
    this.getList();
    this.getdataSourceAllList();
  },
  methods: {
     selectIp(ipstutus) {
       if(ipstutus=== '1'){
         this.ip = true
       }else{
         this.ip = false
       }
     },
    add() {
          this.modules.push('');  // 每点一下，push一次
        },
     beforeCreate: function() {
        that = this;
      },
      mounted() {
        // 页面加载时把数据遍历后 - push到modules数组里面
        for(var item of this.list) {
          this.modules.push({ip:item});
        }
      },
    /** 查询数据API列表 */
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then(response => {
        this.configList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getdataSourceAllList() {
      getdataSourceAll().then(response => {
        this.dataSourceList = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        apiName: null,
        dataSourceId: null,
        querySql: null,
        parameter: null,
        publickey: null,
        privatekey: null,
        requestMode: null,
        ipRights: null,
        expirationTime: null,
        apiState: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        ipAddress: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据API";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改数据API";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除数据API编号为"' + ids + '"的数据项？').then(function() {
        return delConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('apiconfig/config/export', {
        ...this.queryParams
      }, `config_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
