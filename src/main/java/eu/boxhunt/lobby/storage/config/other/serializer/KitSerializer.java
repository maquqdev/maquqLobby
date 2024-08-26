package eu.boxhunt.lobby.storage.config.other.serializer;

import eu.boxhunt.lobby.object.Kit;
import eu.boxhunt.lobby.utils.SerializationUtil;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class KitSerializer implements ObjectSerializer<Kit> {

    @Override
    public boolean supports(@NonNull Class<? super Kit> type) {
        return Kit.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Kit object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("contents", SerializationUtil.serializeObject(object.getContents()));
        data.add("offHand", SerializationUtil.serializeObject(object.getOffHand()));
    }

    @Override
    public Kit deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Kit(
                (ItemStack[]) SerializationUtil.deserializeObject(data.get("contents", String.class)),
                (ItemStack) SerializationUtil.deserializeObject(data.get("offHand", String.class))
        );
    }
}
