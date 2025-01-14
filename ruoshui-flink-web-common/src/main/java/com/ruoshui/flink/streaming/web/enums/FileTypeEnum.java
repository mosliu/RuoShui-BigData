package com.ruoshui.flink.streaming.web.enums;

import lombok.Getter;

@Getter
public enum FileTypeEnum {

  JAR(1);

  private int code;

  FileTypeEnum(int code) {
    this.code = code;
  }
}
