package org.leondorus.remill.domain.drugs

import androidx.compose.runtime.toMutableStateMap
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.leondorus.remill.domain.model.Drug
import org.leondorus.remill.domain.model.DrugId

class DrugEditUseCaseTest {
    @Test
    fun `throws exception on empty repo in update`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val drugInfo = listOf(2 to "", 1 to "drug 1", 10 to "other drug", 3 to "drug3")
        val drugs = drugInfo.map { Drug(DrugId(it.first), it.second, null) }

        drugs.forEach {
            drug ->
            try {
                useCase.updateDrug(drug)
            } catch (e: NoDrugWithSuchId) {
                assert(true)
            }
        }
    }

    @Test
    fun `throws exception on empty repo in delete`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val drugInfo = listOf(2 to "", 1 to "drug 1", 10 to "other drug", 3 to "drug3")
        val drugs = drugInfo.map { Drug(DrugId(it.first), it.second, null) }

        drugs.forEach {
                drug ->
            try {
                useCase.updateDrug(drug)
            } catch (e: NoDrugWithSuchId) {
                assert(true)
            }
        }
    }

    @Test
    fun `add returns drug with the same name`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val name1 = "drug something"
        val name2 = "some drug"

        val drug1 = useCase.addDrug(name1)
        val drug2 = useCase.addDrug(name2)

        assertEquals(name1, drug1.name)
        assertEquals(name2, drug2.name)
    }

    @Test
    fun `update and delete run fine after add`() = runTest {
        val repo = SimpleEditRepo(listOf())
        val useCase = DrugEditUseCase(repo)
        val oldName = "old name"
        val newName = "new name"

        val drug = useCase.addDrug(oldName)
        useCase.updateDrug(Drug(drug.id, newName, null))
        useCase.deleteDrug(drug.id)
    }
}

class SimpleEditRepo(initDrugs: Iterable<Pair<DrugId, Drug>>) : DrugEditRepo {
    private val drugs: MutableMap<DrugId, Drug> = HashMap()
    init {
        for (pair in initDrugs) {
            drugs[pair.first] = pair.second
        }
    }
    override suspend fun addDrug(name: String): Drug {
        var newId = DrugId(0)
        for (i in 0..Int.MAX_VALUE) {
            if (!drugs.contains(DrugId(i))) {
                newId = DrugId(i)
                break
            }
        }

        val drug = Drug(newId, name, null)
        drugs[newId] = drug
        return drug
    }

    override suspend fun updateDrug(drug: Drug) {
        val contains = drugs.contains(drug.id)
        if (!contains) {
            throw NoDrugWithSuchId()
        }
        drugs[drug.id] = drug
    }

    override suspend fun deleteDrug(drugId: DrugId) {
        val contains = drugs.contains(drugId)
        if (!contains) {
            throw NoDrugWithSuchId()
        }
        drugs.remove(drugId)
    }
}