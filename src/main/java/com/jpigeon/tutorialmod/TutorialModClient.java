package com.jpigeon.tutorialmod;

import com.jpigeon.ridebattlelib.example.ExampleBasic;
import com.jpigeon.tutorialmod.rider.DynamicRider;
import com.jpigeon.tutorialmod.rider.MyRider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TutorialMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TutorialMod.MODID, value = Dist.CLIENT)
public class TutorialModClient {
    public TutorialModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // 永远在这初始化骑士配置
        // ExampleBasic.init();
        MyRider.init();
        // DynamicRider.init();
    }
}
