package org.leondorus.remill.domain.drugs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugGetUseCaseTest {
    @Test
    fun `is empty if repo is empty`() = runTest {
        val repo = SimpleGetRepo(listOf())
        val getUseCase = DrugGetUseCase(repo)
        val ids = listOf(DrugId(0), DrugId(1), DrugId(2), DrugId(10))

        val results = ids.map { id -> getUseCase.getDrug(id).first() }

        for (res in results) {
            assertEquals(null, res)
        }
    }

    @Test
    fun `returns some if there is some`() = runTest {
        val drugList = listOf(0 to "drug0", 1 to "drug1", 124 to "some drug").map {
            Drug(DrugId(it.first), it.second, null, null)
        }
        val repo = SimpleGetRepo(drugList)
        val useCase = DrugGetUseCase(repo)

        val allDrugs = useCase.getAllDrugs().first()
        val allDrugsIndividually = drugList.map { drug -> useCase.getDrug(drug.id).first() }

        assertEquals(drugList, allDrugs)
        assertEquals(drugList, allDrugsIndividually)
    }

    @Test
    fun `do not returns some if there is no such`() = runTest {
        val drugList = listOf(0 to "drug0", 1 to "drug1", 124 to "some drug").map {
            Drug(DrugId(it.first), it.second, null, null)
        }
        val otherIds = listOf(102, 343, 532, 2, 3).map { DrugId(it) }

        val repo = SimpleGetRepo(drugList)
        val useCase = DrugGetUseCase(repo)

        val getResults = otherIds.map { id -> useCase.getDrug(id).first() }

        for (res in getResults) {
            assertEquals(null, res)
        }
    }
}

class SimpleGetRepo(private val drugs: List<Drug>) : DrugGetRepo {
    override fun getDrug(id: DrugId): Flow<Drug?> {
        val drug = drugs.find { drug -> drug.id == id }
        return flow {
            emit(drug)
        }
    }

    override fun getAllDrugs(): Flow<List<Drug>> {
        return flow {
            emit(drugs)
        }
    }
}