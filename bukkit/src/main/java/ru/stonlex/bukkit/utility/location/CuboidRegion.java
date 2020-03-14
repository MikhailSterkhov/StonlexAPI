package ru.stonlex.bukkit.utility.location;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class CuboidRegion implements Iterable<Block>, Cloneable, ConfigurationSerializable {

    /**
     * Этот код я вообще взял с интернета, поэтому он неоч,
     * но сам по себе очень даже юзабельный
     */

    private String worldName;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;
    private List<Block> blocks;
    private Location pos1;
    private Location pos2;

    public CuboidRegion(Location l1, Location l2) {
        this.pos1 = l1;
        this.pos2 = l2;
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
    }

    public CuboidRegion(Location l1) {
        this(l1, l1);
    }

    public CuboidRegion(CuboidRegion other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }

    public CuboidRegion(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.pos1 = new Location(world, x1, y1, z1);
        this.pos2 = new Location(world, x2, y2, z2);
        this.worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    private CuboidRegion(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.pos1 = new Location(Bukkit.getWorld(worldName), x1, y1, z1);
        this.pos2 = new Location(Bukkit.getWorld(worldName), x2, y2, z2);
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public CuboidRegion(Map<String, Object> map) {
        this.worldName = (String)map.get("worldName");
        this.x1 = (Integer)map.get("x1");
        this.x2 = (Integer)map.get("x2");
        this.y1 = (Integer)map.get("y1");
        this.y2 = (Integer)map.get("y2");
        this.z1 = (Integer)map.get("z1");
        this.z2 = (Integer)map.get("z2");
        this.pos1 = new Location(Bukkit.getWorld(this.worldName), this.x1, this.y1, this.z1);
        this.pos2 = new Location(Bukkit.getWorld(this.worldName), this.x2, this.y2, this.z2);
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("worldName", this.worldName);
        map.put("x1", this.x1);
        map.put("y1", this.y1);
        map.put("z1", this.z1);
        map.put("x2", this.x2);
        map.put("y2", this.y2);
        map.put("z2", this.z2);
        return map;
    }

    public Location getPosition2() {
        return this.pos2;
    }

    public Location getPosition1() {
        return this.pos1;
    }

    public Location getLowerNE() {
        return new Location(this.getWorld(), this.x1, this.y1, this.z1);
    }

    public Location getUpperSW() {
        return new Location(this.getWorld(), this.x2, this.y2, this.z2);
    }

    public List<Block> getBlocks() {
        if (this.blocks != null) {
            return this.blocks;
        }
        this.blocks = new ArrayList<>();
        for (Block block : this) {
            this.blocks.add(block);
        }
        return this.blocks;
    }

    public Location getCenter() {
        int x1 = this.getUpperX() + 1;
        int y1 = this.getUpperY() + 1;
        int z1 = this.getUpperZ() + 1;
        return new Location(this.getWorld(), (double)this.getLowerX() + (double)(x1 - this.getLowerX()) / 2.0, (double)this.getLowerY() + (double)(y1 - this.getLowerY()) / 2.0, (double)this.getLowerZ() + (double)(z1 - this.getLowerZ()) / 2.0);
    }

    public World getWorld() {
        World world = Bukkit.getWorld(this.worldName);
        if (world == null) {
            throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
        }
        return world;
    }

    public int getSizeX() {
        return this.x2 - this.x1 + 1;
    }

    public int getSizeY() {
        return this.y2 - this.y1 + 1;
    }

    public int getSizeZ() {
        return this.z2 - this.z1 + 1;
    }

    public int getLowerX() {
        return this.x1;
    }

    public int getLowerY() {
        return this.y1;
    }

    public int getLowerZ() {
        return this.z1;
    }

    public int getUpperX() {
        return this.x2;
    }

    public int getUpperY() {
        return this.y2;
    }

    public int getUpperZ() {
        return this.z2;
    }

    public Block[] corners() {
        Block[] res = new Block[8];
        World w = this.getWorld();
        res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
        res[1] = w.getBlockAt(this.x1, this.y1, this.z2);
        res[2] = w.getBlockAt(this.x1, this.y2, this.z1);
        res[3] = w.getBlockAt(this.x1, this.y2, this.z2);
        res[4] = w.getBlockAt(this.x2, this.y1, this.z1);
        res[5] = w.getBlockAt(this.x2, this.y1, this.z2);
        res[6] = w.getBlockAt(this.x2, this.y2, this.z1);
        res[7] = w.getBlockAt(this.x2, this.y2, this.z2);
        return res;
    }

    public CuboidRegion expand(CuboidDirection dir, int amount) {
        switch (dir) {
            case North: {
                return new CuboidRegion(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case South: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
            }
            case East: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
            }
            case West: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
            }
            case Down: {
                return new CuboidRegion(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
            }
            case Up: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
            }
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public CuboidRegion shift(CuboidDirection dir, int amount) {
        return this.expand(dir, amount).expand(dir.opposite(), -amount);
    }

    public CuboidRegion outset(CuboidDirection dir, int amount) {
        CuboidRegion c;
        switch (dir) {
            case Horizontal: {
                c = this.expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount).expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount);
                break;
            }
            case Vertical: {
                c = this.expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
                break;
            }
            case Both: {
                c = this.outset(CuboidDirection.Horizontal, amount).outset(CuboidDirection.Vertical, amount);
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + dir);
            }
        }
        return c;
    }

    public CuboidRegion inset(CuboidDirection dir, int amount) {
        return this.outset(dir, -amount);
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    public boolean contains(Block b) {
        return this.contains(b.getLocation());
    }

    public boolean contains(Location l) {
        if (!this.worldName.equals(l.getWorld().getName())) {
            return false;
        }
        return this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    public int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }

    public byte getAverageLightLevel() {
        long total = 0L;
        int n = 0;
        for (Block b : this) {
            if (!b.isEmpty()) continue;
            total += b.getLightLevel();
            ++n;
        }
        return n > 0 ? (byte)(total / (long)n) : (byte)0;
    }

    public CuboidRegion contract() {
        return this.contract(CuboidDirection.Down).contract(CuboidDirection.South).contract(CuboidDirection.East).contract(CuboidDirection.Up).contract(CuboidDirection.North).contract(CuboidDirection.West);
    }

    public CuboidRegion contract(CuboidDirection dir) {
        CuboidRegion face = this.getFace(dir.opposite());
        switch (dir) {
            case Down: {
                while (face.containsOnly(0) && face.getLowerY() > this.getLowerY()) {
                    face = face.shift(CuboidDirection.Down, 1);
                }
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, face.getUpperY(), this.z2);
            }
            case Up: {
                while (face.containsOnly(0) && face.getUpperY() < this.getUpperY()) {
                    face = face.shift(CuboidDirection.Up, 1);
                }
                return new CuboidRegion(this.worldName, this.x1, face.getLowerY(), this.z1, this.x2, this.y2, this.z2);
            }
            case North: {
                while (face.containsOnly(0) && face.getLowerX() > this.getLowerX()) {
                    face = face.shift(CuboidDirection.North, 1);
                }
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, face.getUpperX(), this.y2, this.z2);
            }
            case South: {
                while (face.containsOnly(0) && face.getUpperX() < this.getUpperX()) {
                    face = face.shift(CuboidDirection.South, 1);
                }
                return new CuboidRegion(this.worldName, face.getLowerX(), this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case East: {
                while (face.containsOnly(0) && face.getLowerZ() > this.getLowerZ()) {
                    face = face.shift(CuboidDirection.East, 1);
                }
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.getUpperZ());
            }
            case West: {
                while (face.containsOnly(0) && face.getUpperZ() < this.getUpperZ()) {
                    face = face.shift(CuboidDirection.West, 1);
                }
                return new CuboidRegion(this.worldName, this.x1, this.y1, face.getLowerZ(), this.x2, this.y2, this.z2);
            }
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public CuboidRegion getFace(CuboidDirection dir) {
        switch (dir) {
            case Down: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
            }
            case Up: {
                return new CuboidRegion(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
            }
            case North: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
            }
            case South: {
                return new CuboidRegion(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case East: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
            }
            case West: {
                return new CuboidRegion(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
            }
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public boolean containsOnly(int blockId) {
        for (Block b : this) {
            if (b.getTypeId() == blockId) continue;
            return false;
        }
        return true;
    }

    public CuboidRegion getBoundingCuboid(CuboidRegion other) {
        if (other == null) {
            return this;
        }
        int xMin = Math.min(this.getLowerX(), other.getLowerX());
        int yMin = Math.min(this.getLowerY(), other.getLowerY());
        int zMin = Math.min(this.getLowerZ(), other.getLowerZ());
        int xMax = Math.max(this.getUpperX(), other.getUpperX());
        int yMax = Math.max(this.getUpperY(), other.getUpperY());
        int zMax = Math.max(this.getUpperZ(), other.getUpperZ());
        return new CuboidRegion(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(int x, int y, int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public Block getRelativeBlock(World w, int x, int y, int z) {
        return w.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public List<Chunk> getChunks() {
        ArrayList<Chunk> res = new ArrayList<>();
        World w = this.getWorld();
        int x1 = this.getLowerX() & -16;
        int x2 = this.getUpperX() & -16;
        int z1 = this.getLowerZ() & -16;
        int z2 = this.getUpperZ() & -16;
        for (int x = x1; x <= x2; x += 16) {
            for (int z = z1; z <= z2; z += 16) {
                res.add(w.getChunkAt(x >> 4, z >> 4));
            }
        }
        return res;
    }

    @Override
    public Iterator<Block> iterator() {
        return new CuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    public CuboidRegion clone() {
        return new CuboidRegion(this);
    }

    public String toString() {
        return this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2;
    }

    public List<Block> getEndLines() {
        CuboidRegion cuboidRegion1 = new CuboidRegion(
                this.corners()[0].getLocation(),
                this.corners()[1].getLocation());
        ArrayList<Block> list = new ArrayList<>(cuboidRegion1.getBlocks());

        CuboidRegion cuboidRegion2 = new CuboidRegion(
                this.corners()[0].getLocation(),
                this.corners()[2].getLocation());
        list.addAll(cuboidRegion2.getBlocks());

        CuboidRegion cuboidRegion3 = new CuboidRegion(
                this.corners()[0].getLocation(),
                this.corners()[4].getLocation());
        list.addAll(cuboidRegion3.getBlocks());

        CuboidRegion cuboidRegion4 = new CuboidRegion(
                this.corners()[1].getLocation(),
                this.corners()[3].getLocation());
        list.addAll(cuboidRegion4.getBlocks());

        CuboidRegion cuboidRegion5 = new CuboidRegion(
                this.corners()[1].getLocation(),
                this.corners()[5].getLocation());
        list.addAll(cuboidRegion5.getBlocks());

        CuboidRegion cuboidRegion6 = new CuboidRegion(
                this.corners()[2].getLocation(),
                this.corners()[3].getLocation());
        list.addAll(cuboidRegion6.getBlocks());

        CuboidRegion cuboidRegion7 = new CuboidRegion(
                this.corners()[2].getLocation(),
                this.corners()[6].getLocation());
        list.addAll(cuboidRegion7.getBlocks());

        CuboidRegion cuboidRegion8 = new CuboidRegion(
                this.corners()[3].getLocation(),
                this.corners()[7].getLocation());
        list.addAll(cuboidRegion8.getBlocks());

        CuboidRegion cuboidRegion9 = new CuboidRegion(
                this.corners()[4].getLocation(),
                this.corners()[5].getLocation());
        list.addAll(cuboidRegion9.getBlocks());

        CuboidRegion cuboidRegion10 = new CuboidRegion(
                this.corners()[4].getLocation(),
                this.corners()[6].getLocation());
        list.addAll(cuboidRegion10.getBlocks());

        CuboidRegion cuboidRegion11 = new CuboidRegion(
                this.corners()[5].getLocation(),
                this.corners()[7].getLocation());
        list.addAll(cuboidRegion11.getBlocks());

        CuboidRegion cuboidRegion12 = new CuboidRegion(
                this.corners()[7].getLocation(),
                this.corners()[6].getLocation());
        list.addAll(cuboidRegion12.getBlocks());

        return list;
    }

    public enum CuboidDirection {
        North,
        East,
        South,
        West,
        Up,
        Down,
        Horizontal,
        Vertical,
        Both,
        Unknown;
        

        public CuboidDirection opposite() {
            switch (this) {
                case North: {
                    return South;
                }
                case East: {
                    return West;
                }
                case South: {
                    return North;
                }
                case West: {
                    return East;
                }
                case Horizontal: {
                    return Vertical;
                }
                case Vertical: {
                    return Horizontal;
                }
                case Up: {
                    return Down;
                }
                case Down: {
                    return Up;
                }
                case Both: {
                    return Both;
                }
            }
            return Unknown;
        }
    }

    public static class CuboidIterator implements Iterator<Block> {

        private World w;
        private int baseX;
        private int baseY;
        private int baseZ;
        private int x;
        private int y;
        private int z;
        private int sizeX;
        private int sizeY;
        private int sizeZ;

        public CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.z = 0;
            this.y = 0;
            this.x = 0;
        }

        @Override
        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        @Override
        public Block next() {
            Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            if (++this.x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return b;
        }

        @Override
        public void remove() { }
    }

}

