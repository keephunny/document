https://blog.csdn.net/basenet855x/article/details/6826731
int[] executeBatch() ：
    // 关闭自动执行 
    con.setAutoCommit(false); 
    Statement stmt = con.createStatement(); 
    stmt.addBatch("INSERT INTO employees VALUES (1000, 'Joe Jones')"); 
    stmt.addBatch("INSERT INTO departments VALUES (260, 'Shoe')"); 
    stmt.addBatch("INSERT INTO emp_dept VALUES (1000, 260)"); 
    // 提交一批要执行的更新命令 
    int[] updateCounts = stmt.executeBatch();
    void close() throws SQLException;


    https://www.cnblogs.com/tommy-huang/p/4540407.html

    https://www.2cto.com/kf/201608/542346.html


    executeBatch
    executeLargeBatch