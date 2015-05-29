#include <linux/kernel.h>
#include <linux/unistd.h>
#include <linux/sched.h>

void sys_my_get_proc_name_by_id(int pid, char *user_buff)
{
	struct task_struct *t;
	t = find_task_by_vpid(pid);

	if(t == NULL)
		return;

	char buf[17];
	get_task_comm(buf, t);
	buf[16] = '\0';

	copy_to_user(user_buff, buf, 17);
	return;
}

