<template>
  <el-card class="box-card" shadow="always">
    <div slot="header" class="clearfix">
      <span>{{ title }}</span>
      <el-button-group style="float: right;">
        <el-button size="mini" icon="el-icon-back" round @click="showCard">返回</el-button>
      </el-button-group>
    </div>
    <div class="body-wrapper">
      <el-form ref="form" :model="form" label-width="80px" disabled>
        <el-form-item label="数据源" prop="sourceName">
          <el-input v-model="form.sourceName" />
        </el-form-item>
        <el-form-item label="数据库表" prop="tableName">
          <el-input v-model="form.tableName" />
        </el-form-item>
        <el-form-item label="变更字段" prop="fieldName">
          <el-input v-model="fieldName" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" />
        </el-form-item>
        <el-form-item label="原来的值" prop="fieldOldValue">
          <el-input v-model="form.fieldOldValue" />
        </el-form-item>
        <el-form-item label="最新的值" prop="fieldNewValue">
          <el-input v-model="form.fieldNewValue" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status">
              <el-option
              v-for="dict in dict.type.sys_data_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>

<script>
import { getChangeRecord } from '@/api/metadata/changerecord'

export default {
  name: 'ChangeRecordDetail',
  dicts: ['sys_data_status'],
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
      title: '变更记录详情',
      // 展示切换
      showOptions: {
        data: {},
        showList: true,
        showAdd: false,
        showEdit: false,
        showDetail: false
      },
      // 表单参数
      form: {},
      // 状态数据字典
      statusOptions: [],
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
  computed: {
    fieldName() {
      return this.dicts.get(this.form.fieldName)
    }
  },
  created() {
    console.log('id:' + this.data.id)
    this.getDicts('sys_common_status').then(response => {
      if (response.code == 200) {
        this.statusOptions = response.data
      }
    })
  },
  mounted() {
    this.getChangeRecord(this.data.id)
  },
  methods: {
    showCard() {
      this.$emit('showCard', this.showOptions)
    },
    /** 获取详情 */
    getChangeRecord: function(id) {
      getChangeRecord(id).then(response => {
        if (response.code == 200) {
          this.form = response.data
        }
      })
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
