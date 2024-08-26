package eu.boxhunt.lobby.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ItemUtil {

    private final ItemStack item;
    private final ItemMeta meta;
    private List<String> lore = new ArrayList<>();



    public ItemUtil(Material material, int amount) {
        this(material, amount, (short)0);
    }
    public ItemUtil(Material material, int amount, short data) {
        this.item = new ItemStack(material, amount, data);
        this.meta = this.item.getItemMeta();
    }
    public ItemUtil(Material material) {
        this(material, 1, (short)0);
    }


    public ItemUtil setName(String name) {
        if (name == null) return this;

        name = TextUtil.fixColor(name);
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemUtil addEnchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemUtil setGlowing(boolean t) {
        if (t) {
            this.meta.addEnchant(Enchantment.LUCK, 1, true);
            this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            if (this.meta.getEnchants().containsKey(Enchantment.LUCK))
                this.meta.removeEnchant(Enchantment.LUCK);
            this.meta.removeItemFlags(
                    ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemUtil addLore(String... lore) {
        this.lore.addAll(Arrays.stream(lore).map(TextUtil::fixColor).toList());
        return this;
    }

    public ItemUtil setUnbreakable() {
        this.meta.setUnbreakable(true);
        return this;
    }

    public ItemUtil setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }


    public ItemUtil addLore(List<String> lore) {
        this.lore.addAll(lore.stream().map(TextUtil::fixColor).toList());
        return this;
    }

    public ItemUtil addLorePlaceholder(String from, String to) {
        this.lore = this.lore.stream().map(s -> s.replace(from, to)).toList();
        return this;
    }

    public ItemStack build() {
        this.meta.setLore(this.lore);
        this.item.setItemMeta(this.meta);
        return this.item;
    }

}