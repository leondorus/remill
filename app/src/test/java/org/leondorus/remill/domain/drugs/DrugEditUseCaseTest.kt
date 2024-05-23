package org.leondorus.remill.domain.drugs

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugEditUseCaseTest {
    @Test
    fun `return false on empty repo in update`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val drugInfo = listOf(2 to "", 1 to "drug 1", 10 to "other drug", 3 to "drug3")
        val drugs = drugInfo.map { Drug(DrugId(it.first), it.second, listOf()) }

        val reses = drugs.map { drug -> useCase.updateDrug(drug) }

        for (res in reses) {
            assertEquals(false, res)
        }
    }

    @Test
    fun `return false on empty repo in delete`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val drugInfo = listOf(2 to "", 1 to "drug 1", 10 to "other drug", 3 to "drug3")
        val drugs = drugInfo.map { Drug(DrugId(it.first), it.second, listOf()) }

        val reses = drugs.map { drug -> useCase.deleteDrug(drug.id) }

        for (res in reses) {
            assertEquals(false, res)
        }
    }

    @Test
    fun `second update and delete return true`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val oldDrug = Drug(DrugId(1), "old name", listOf())
        val newDrug = Drug(DrugId(1), "new name", listOf())

        val oldRes = useCase.updateDrug(oldDrug)
        val newRes = useCase.updateDrug(newDrug)
        val deleteRes = useCase.deleteDrug(newDrug.id)

        assertEquals(false, oldRes)
        assertEquals(true, newRes)
        assertEquals(true, deleteRes)
    }
}

class SimpleEditRepo(initDrugs: Iterable<DrugId>) : DrugEditRepo {
    private val drugs: MutableSet<DrugId> = initDrugs.toMutableSet()
    override suspend fun updateDrug(drug: Drug): Boolean {
        val res = drugs.contains(drug.id)
        drugs.add(drug.id)
        return res
    }

    override suspend fun deleteDrug(drugId: DrugId): Boolean {
        val res = drugs.contains(drugId)
        drugs.remove(drugId)
        return res
    }
}