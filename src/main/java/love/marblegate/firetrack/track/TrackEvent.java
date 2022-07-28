package love.marblegate.firetrack.track;

import love.marblegate.firetrack.FireTrack;
import love.marblegate.firetrack.capability.TrackTypeData;
import love.marblegate.firetrack.easteregg.ItemRegistry;
import love.marblegate.firetrack.easteregg.ParticleTypeRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FireTrack.MOD_ID)
public class TrackEvent {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            var player = event.player;
            var shoes = player.getItemBySlot(EquipmentSlot.FEET);
            if(!shoes.isEmpty()){
                var data = shoes.getCapability(TrackTypeData.CAPABILITY);
                data.ifPresent(cap->{
                    var type = cap.get();
                    switch (type){
                        case NONE -> {}
                        case FIRE -> {
                            addParticle(player.level, ParticleTypes.LAVA,player.getX(),player.getY(),player.getZ(), 0.1, 0.1,0.1,0.5,5);
                            if(player.level.getDayTime()%20==0)
                                easterEggDetect(player, ItemRegistry.FIRE_TRACK_FLAME.get());


                        }
                        case SMOKE -> {
                            addParticle(player.level, ParticleTypes.LARGE_SMOKE,player.getX(),player.getY(),player.getZ(), 0.05, 0.05,0.05,0.05,5);
                            if(player.level.getDayTime()%20==0)
                                easterEggDetect(player, ItemRegistry.SMOKE_TRACK_FLAME.get());

                        }
                        case LAVA -> {
                            addParticle(player.level, ParticleTypes.FALLING_LAVA,player.getX(),player.getY(),player.getZ(), 0.2, 0.2,0.2,0.5,10);
                            if(player.level.getDayTime()%20==0)
                                easterEggDetect(player, ItemRegistry.LAVA_TRACK_FLAME.get());

                        }
                        // Easter Egg
                        case MODERN -> addParticle(player.level, ParticleTypeRegistry.TEACON_MODERN.get(),player.getX(),player.getY(),player.getZ(), 0.1, 0.1,0.1,0.05,5);
                        case WAITING_FOR_SERVER -> addParticle(player.level, ParticleTypeRegistry.TEACON_WAITING_FOR_SERVER.get(),player.getX(),player.getY(),player.getZ(), 0.1, 0.1,0.1,0.05,5);
                        case GOOD_NEWS -> addParticle(player.level, ParticleTypeRegistry.TEACON_GOOD_NEWS.get(),player.getX(),player.getY(),player.getZ(), 0.1, 0.1,0.1,0.05,5);
                    }
                });
            }
        }
    }

    private static <T extends ParticleOptions> void addParticle(Level level, T particle, double x, double y, double z, double xDist, double yDist, double zDist, double maxSpeed, int count) {
        ((ServerLevel) level).sendParticles(particle, x, y, z, count, xDist, yDist, zDist, maxSpeed);
    }

    private static void easterEggDetect(Player player, Item give){
        if(!player.level.getEntities(player,new AABB(player.blockPosition()).expandTowards(1,1,0).expandTowards(-1,-1,-1),entity -> entity instanceof Player).isEmpty())
            player.getInventory().placeItemBackInInventory(give.getDefaultInstance());
    }

}
