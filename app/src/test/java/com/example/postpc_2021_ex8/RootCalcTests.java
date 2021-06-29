package com.example.postpc_2021_ex8;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
public class RootCalcTests {

    private RootsApplication app;

    @Before
    public void setup()
    {
        app = Mockito.mock(RootsApplication.class);
        Mockito.when(app.getDataHolder()).thenReturn(new RootsHolder());
    }

    @Test
    public void when_addingRootToCalculate_then_rootsHolderShouldHaveThisItem() {

        RootsHolder holder = app.getDataHolder();
        assertEquals(0, holder.getCurrentList().size());

        holder.addNewItem(new RootCalc(UUID.randomUUID(),4));
        assertEquals(1, holder.getCurrentList().size());
    }

    @Test
    public void when_deletingRoot_then_rootsHolderShouldNotHaveThisItem()
    {
        RootsHolder holder = app.getDataHolder();
        RootCalc r = new RootCalc(UUID.randomUUID(), 4);
        holder.addNewItem(r);
        assertEquals(1, holder.getCurrentList().size());

        holder.deleteRoot(r);
        assertEquals(0, holder.getCurrentList().size());
    }

    @Test
    public void when_addingALotOfRoots_then_theyNeedToBeSortedByValue()
    {
        RootsHolder holder = app.getDataHolder();
        holder.addNewItem(new RootCalc(UUID.randomUUID(),4));
        holder.addNewItem(new RootCalc(UUID.randomUUID(),14));
        holder.addNewItem(new RootCalc(UUID.randomUUID(),12));
        holder.addNewItem(new RootCalc(UUID.randomUUID(),5));
        holder.addNewItem(new RootCalc(UUID.randomUUID(),8));

        assertEquals(4L,holder.getCurrentList().get(0).getNumber());
        assertEquals(5L,holder.getCurrentList().get(1).getNumber());
        assertEquals(8L,holder.getCurrentList().get(2).getNumber());
        assertEquals(12L,holder.getCurrentList().get(3).getNumber());
        assertEquals(14L,holder.getCurrentList().get(4).getNumber());

    }
}
