package init;

import java.util.Random;

import org.liws.mybatis.dao.IUserDao;
import org.liws.mybatis.dao.base.DAOFactory;
import org.liws.mybatis.model.User;

/**
 * 往users表预置一些用户
 */
public class UsersInitMain {
	
	static String[] firstName = new String[] { "张", "刘", "牛", "李", "吕", "付",
			"副", "王", "汪", "赵", "孔", "谭", "贪", "夏侯", "令狐", "上官", "欧阳", "司马",
			"诸葛", "曹", "关", "孙", "甘" };
	static String[] centerName = { "立", "祝", "共", "都", "高", "陆", "恶", "人", "达",
			"玉", "莫", "靓", "宇" };
	static String[] lastName = { "超", "强", "颖", "备", "亮", "云", "正", "冲", "兄弟", "大",
			"小", "关", "撒旦", "立", "玉", "鱼", "牛", "泵", "秒", "米", "个", "鐜", "惇",
			"彦", "另", "琳", "浩", "皓", "永锋", "明正", "丽音", "志峰", "春" };
	
	static Random ran = new Random();
	private static String ranName() {
		int num = ran.nextInt(10);
		if (num < 5) {
			// 三个字
			return firstName[ran.nextInt(firstName.length)]
			                 +centerName[ran.nextInt(centerName.length)]
			                 +lastName[ran.nextInt(lastName.length)];
		} else {
			// 两个字
			return firstName[ran.nextInt(firstName.length)]
			                 +lastName[ran.nextInt(lastName.length)];
		}
	}

	public static void main(String[] args) {
		IUserDao userDao = DAOFactory.getUserDao();
		for (int i = 0; i < 300; i++) {
			User u = new User();
			u.setNickname(ranName());
			u.setPassword("123");
			u.setType(0);
			u.setUsername("user" + i);
			
			userDao.add(u);
		}
	}
}
