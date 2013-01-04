package org.sdu.database;

/**
 * Store database column data.
 * 
 * @version 0.1 rev 8000 Jan. 4, 2013
 * Copyright (c) HyperCube Dev Team
 */
public class Detail {
	final static String[][] column = {
			{ "姓名", "英文姓名", "性别", "出生日期", "国别", "民族", "政治面貌" },
			{ "证件类别", "证件号码", "所在地", "家庭地址", "家庭邮编", "家庭电话", "固定电话" },
			{ "籍贯", "通讯地址", "邮编", "移动电话", "QQ号", "MSN", "电子邮件" },
			{ "婚否", "华侨", "英烈子女", "健康状况", "特长", "经济状况", "经济来源" },
			{ "入学日期", "所属院系", "学生类型", "所属专业", "所属年级", "所在班级", "所属校区" },
			{ "授学位", "研究方向", "培养方式", "学习方式", "学制", "已获学历", "学位类别" },
			{ "外语语种", "外语水平", "学籍", "专业领域", "学习地点", "准考证号", "校园卡号" } };
	final static String[][] columnName = {
			{ "name", "engname", "sex", "birth", "country", "nation",
					"identity" },
			{ "idtype", "idnum", "home", "homeadd", "homepost", "homephone",
					"telephone" },
			{ "hometown", "address", "postcode", "mobile", "qq", "msn", "email" },
			{ "married", "oversea", "hero", "health", "talent", "economy",
					"moneysource" },
			{ "entrydate", "faculty", "type", "profession", "grade", "class",
					"region" },
			{ "title", "direction", "techmethod", "studymethod", "studytype",
					"background", "bachelor" },
			{ "language", "languageskill", "roll", "field", "studyplace",
					"exam", "cardnum" } };
	final static int[][] columnType = { { 1, 1, 4, 3, 1, 4, 4 },
			{ 4, 1, 1, 1, 2, 1, 1 }, { 1, 1, 2, 1, 2, 1, 1 },
			{ 4, 4, 4, 1, 1, 1, 1 }, { 3, 4, 4, 4, 2, 1, 4 },
			{ 4, 1, 4, 4, 4, 4, 4 }, { 1, 1, 4, 4, 4, 2, 2 } };
}
