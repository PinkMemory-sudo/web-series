package com.pk.jpa.model;

/**
 * 常量类
 */
public class Constants {
    public static final String DASH = "-";
    public static final String Y_DASH = "|";
    public static final String COMMA = ",";
    /**
     * 全角的逗号
     */
    public static final String FULL_COMMA = "，";
    public static final String COLON = ":";
    // 常量-1
    public static final int MINUS_ONE = -1;
    // 常量0
    public static final int ZERO = 0;
    // 常量1
    public static final int ONE = 1;
    // 常量2
    public static final int TWO = 2;
    // 常量3
    public static final int THREE = 3;
    // 常量4
    public static final int FOUR = 4;
    // 常量5
    public static final int FIVE = 5;
    // 常量10
    public static final int TEN = 10;
    // long型常量1
    public static final long LONG_ONE = 1L;
    /**
     * long型常量2
     */
    public static final long LONG_TWO = 2L;
    // long型常量0
    public static final long LONG_ZERO = 0L;
    public static final String PHONE_REG = "^[1][0-9]{10}$";

    public static final String IMEI_REG = "^[0-9]{14,15}$";

    public static final String MAC_REG =
            "^[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}$";
    // IMSI正则表达式
    public static final String IMSI_REG = "^460(0[0-9]|1[0-2])[0-9]{10}$";

    public static final String LOWERCASE_MAC = "mac";

    public static final String LOWERCASE_PHONE = "phone";

    public static final String LOWERCASE_IMEI = "imei";

    public static final String LOWERCASE_IMSI = "imsi";

    public static final String UPPERCASE_PHONE = "PHONE";

    public static final String UPPERCASE_MAC = "MAC";

    public static final String UPPERCASE_IMEI = "IMEI";

    public static final String UPPERCASE_IMSI = "IMSI";
    // 分隔符^a
    public static final String CTRL_A = "\001";
    // 分隔符^b
    public static final String CTRL_B = "\002";
    // 斜线
    public static final String SLASH = "/";
    // 下划线
    public static final String UNDERSCORE = "_";
    // 空字符串
    public static final String EMPTY_STR = "";
    // 常量100
    public static final int ONE_HUNDRED = 100;
    // 常量1000
    public static final int ONE_THOUSAND = 1000;
    // 常量60000
    public static final int SIXTY_THOUSAND = 60000;
    // 1MB折合的byte数，也即字节数
    public static final long ONE_MB_IN_BYTES = 1048576L;
    // 1KB折合的byte数，也即字节数
    public static final long ONE_KB_IN_BYTES = 1024L;
    // 秒级时间戳类型
    public static final String TIME_TYPE = "s";
    public static final String MILLIS_TIME_TYPE = "ms";
    public static final String TOKEN_AUTHORIZATION = "Authorization";

    public static final String FILTER_URL = "get_intention_role_mapping,add_task_meta,get_intention_dict," +
            "bulk_upsert_intel_target,get_userid_pushcount_mapping,intention_high_risk";
    // 加密密码
    public static final String KEY = "qinlj123";
    public static final String DRUG_ORDER = "role2Es,role3Es,role1Es";

    public static final int DAY_SECOND = 86400;
    public static final String INTENT_SEPARATOR = "#";

    public static final String DEFAULT_INTENT_TYPE = "未知意图";

    public static final String DEFAULT_INTENT_ACTION = "未知行为";

    public static final String WARN_DATA_LOAD_PERMISSION = "warnDataLoadPermission";

    // DAY IN SECONDS
    public static final int DAY_IN_SECONDS = 86400;
    // HALF DAY IN SECONDS
    public static final int HALF_DAY_IN_SECONDS = 43200;

    public static final String INTENTION_AUTH_USER = "ADMIN";

    public static final String INTENTION_AUTH_TOKEN = "b17e2de52679e7a3813a1cd7a253bfd0";

    public static final String INTENTION_DATA_RECORD = "/data/record?perPage=1000";
    public static final String INTENTION_COLUMN = "/setting/intention_column/selectlist";
    public static final String INTENTION_UPDATE_MARK_STATUS = "/push_mark/%d/%d/%d";
    public static final String INTENTION_CREATE_TRANS_BATCH = "/file/createBatch";
    public static final String INTENTION_ADD_FILE_TO_BATCH = "/file/addFileToBatch";
    public static final String INTENTION_START_TRANS_BATCH = "/file/startBatch";

    public static final long INTENTION_SECRET = 20130101666L;
    public static final int INTENTION_SUCCESS_CODE = 0;
    public static final int INTENTION_FAIL_CODE = -1;
    public static final String INTENTION_SUCCESS_MSG = "success";
    public static final int intention_retry = 3;
    public static final int INTENTION_UN_MARK = 1;
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_INTENTION_AUTH_USER = "INTENTION-AUTH-USER";
    public static final String HEADER_INTENTION_AUTH_TOKEN = "INTENTION-AUTH-TOKEN";
    public static final String intention_param1 = "instanceId";
    public static final String intention_param2 = "useridList";
    public static final String intention_param3 = "outputCol";
    public static final long INTENTION_SLEEP = 1;
    public static final String INTENTION_FILTER_OUTCL = "relation,poi,tiebaAction";
    public static final String INTENTION_FILE_SUFFIX = ".encrypt";
    public static final int INTENTION_RECORD_MARK = 2;
    public static final int INTENTION_MARK_PUSHED = 2;

    public static final String Letter_REMIND_TITLE = "开函提醒";
    public static final String Letter_REMIND_MSG
            = "为了保证您的服务能继续正常的使用，请及时开具上月的函件并以纸质版形式寄回，谢谢！";
    public static final String EXPIRE_REMIND_TITLE = "到期提醒";
    public static final String EXPIRE_REMIND_MSG = "到期，如需继续使用，请联系专员处理，谢谢！";
    // 贴吧类型：发帖
    public static final String FORUM_POST = "forum_post";
    public static final String FORUM_POST_TRANS = "发帖";
    // 贴吧类型：回帖
    public static final String FORUM_RE = "forum_re";
    public static final String FORUM_RE_TRANS = "回帖";
    // 贴吧类型：群组
    public static final String GROUP = "group";
    public static final String GROUP_TRANS = "群组";
    /**
     * 人员反馈其他原因说明的字数限制
     */
    public static final int OTHER_REASON_MAX_LENGTH = 128;
    /**
     * 人员反馈备注的字数限制
     */
    public static final int NOTE_MAX_LENGTH = 256;

    /** 文件挂载路径 */
    public static final String MOUNT_PATH = System.getProperty("user.dir") + "/download";
}
