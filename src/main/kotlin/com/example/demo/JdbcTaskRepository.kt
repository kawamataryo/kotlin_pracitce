package com.example.demo

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcTaskRepository(private val jdbcTemplate: JdbcTemplate) : TaskRepository {

    private val rowMapper = RowMapper<Task> { rs, _ ->
        Task(rs.getLong("id"), rs.getString("content"), rs.getBoolean("done"))
    }

    override fun create(content: String): Task {
        jdbcTemplate.update("insert into task(content) values(?)", content)
        val id = jdbcTemplate.queryForObject("select last_insert_id()", Long::class.java)
        return Task(requireNotNull(id), content, false)
    }

    override fun update(task: Task) {
        jdbcTemplate.update("update task set content = ?, done = ? where id = ?",
                task.content, task.done, task.id)
    }

    override fun findAll(): List<Task> =
            jdbcTemplate.query("select id, content, done from task", rowMapper)

    override fun findById(id: Long): Task? =
            jdbcTemplate.query("select id, content, done from task where id = ?",
                    rowMapper, id).firstOrNull()


}