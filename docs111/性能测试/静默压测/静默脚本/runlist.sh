#!/usr/bin/env bash

# 压测脚本中设定的压测时间应为20秒
export jmx_filename="get"

echo "自动化压测开始"

# 压测并发数列表
thread_number_array=(1 2 3)
for num in "${thread_number_array[@]}"

do
    #printf "%s\t" "$num"
    # JMeter 静默压测 + 生成html压测报告 更换IP
    python3 temp_jmx.py $jmx_filename $num 1 1 ceshiren.com
    # JMeter 静默压测 + 生成html压测报告 不更换IP
#    python3 temp_jmx.py $jmx_filename $num 1 1
done
echo "自动化压测全部结束"
