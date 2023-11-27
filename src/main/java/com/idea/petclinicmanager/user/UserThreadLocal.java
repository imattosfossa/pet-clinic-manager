package com.idea.petclinicmanager.user;

import com.idea.petclinicmanager.user.entity.User;

public class UserThreadLocal {
	static InheritableThreadLocal<User> threadLocal = new InheritableThreadLocal<User>();

	public static void set(User user) {

		threadLocal.set(user);

	}

	public static User get() {

		return threadLocal.get();

	}

	public static void clear() {

		threadLocal.remove();

	}
}