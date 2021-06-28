package rmc.libs.tile_ownership;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
public abstract class TileOwnership {

    private static final String OWNER_KEY = "rmc$ownerId";
    private static final String TRUST_KEY = "rmc$trusted";

    public static @Nullable CompoundNBT convert(@Nullable World world, @Nullable BlockPos pos) {
        return world != null && pos != null && world.getBlockState(pos).hasTileEntity() ? convert(world.getBlockEntity(pos)) : null;
    }

    public static @Nullable CompoundNBT convert(@Nullable TileEntity tile) {
        return tile != null ? tile.getTileData() : null;
    }

    public static @Nullable UUID loadOwner(@Nonnull CompoundNBT tag) {
        INBT preId = nbtAction(tag, OWNER_KEY, null);
        return preId != null ? NBTUtil.loadUUID(preId) : null;
    }

    public static void saveOwner(@Nonnull CompoundNBT tag, @Nonnull UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null!");
        }
        nbtAction(tag, OWNER_KEY, NBTUtil.createUUID(id));
    }

    public static boolean isTrustworthy(@Nonnull CompoundNBT tag) {
        return ByteNBT.ONE.equals(nbtAction(tag, TRUST_KEY, null));
    }

    public static void grantTrust(@Nonnull CompoundNBT tag) {
        nbtAction(tag, TRUST_KEY, ByteNBT.valueOf(true));
    }

    private static INBT nbtAction(CompoundNBT tag, String key, INBT val) {
        if (tag == null) {
            throw new IllegalArgumentException("tag must not be null!");
        }
        if (val == null) {
            return tag.get(key);
        }
        else {
            tag.put(key, val);
            return null;
        }
    }

}