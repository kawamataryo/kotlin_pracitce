package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@Sql(statements = arrayOf("delete from task"))
class JdbcTaskRepositoryTest {

    @Autowired
    private lateinit var sut: JdbcTaskRepository

    @Test
    fun update_taskの更新ができること() {
        val task = sut.create("test")
        assertThat(sut.findById(task.id)).isEqualTo(task)
        sut.update(Task(task.id, "editTest", true))
        assertThat(sut.findById(task.id)).isNotEqualTo(task)
        assertThat(sut.findById(task.id)).isEqualTo(Task(task.id, "editTest", true))
    }

    @Test
    fun findAll_何も作成しなければ空のリストを返すこと() {
        val got = sut.findAll()
        assertThat(got).isEmpty()
    }

    @Test
    fun findAll_taskがある場合はそのリストを返すこと() {
        val task1 = sut.create("tes1")
        val task2 = sut.create("tes2")

        val got = sut.findAll()
        assertThat(got[0]).isEqualTo(task1)
        assertThat(got[1]).isEqualTo(task2)
    }

    @Test
    fun findById_createで作成したタスクを取得できること() {
        val task = sut.create("test")
        val got = sut.findById(task.id)
        assertThat(got).isEqualTo(task)
    }

}