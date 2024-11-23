package net.dirius.onsecrethistories.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dirius.onsecrethistories.util.OSHTags;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class TableBlock extends Block implements Waterloggable {
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> FACING_PROPERTIES = (Map<Direction, BooleanProperty>)ConnectingBlock.FACING_PROPERTIES
            .entrySet()
            .stream()
            .filter(entry -> ((Direction)entry.getKey()).getAxis().isHorizontal())
            .collect(Util.toMap());
    protected final VoxelShape[] collisionShapes;
    protected final VoxelShape[] boundingShapes;
    private final Object2IntMap<BlockState> SHAPE_INDEX_CACHE = new Object2IntOpenHashMap<>();

    protected final BlockState baseBlockState;
    protected final Block baseBlock;

    public static final MapCodec<TableBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(BlockState.CODEC.fieldOf("base_state").forGetter(block -> block.baseBlockState), createSettingsCodec())
                    .apply(instance, TableBlock::new)
    );

    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0, 12, 0, 16, 16, 16);
    protected static final VoxelShape NW_LEG_SHAPE = Block.createCuboidShape(2, 0, 2, 6, 12, 6);
    protected static final VoxelShape NE_LEG_SHAPE = Block.createCuboidShape(10, 0, 2, 14, 12, 6);
    protected static final VoxelShape SW_LEG_SHAPE = Block.createCuboidShape(2, 0, 10, 6, 12, 14);
    protected static final VoxelShape SE_LEG_SHAPE = Block.createCuboidShape(10, 0, 10, 14, 12, 14);

    public TableBlock(BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(NORTH, Boolean.valueOf(false))
                        .with(EAST, Boolean.valueOf(false))
                        .with(SOUTH, Boolean.valueOf(false))
                        .with(WEST, Boolean.valueOf(false))
                        .with(WATERLOGGED, Boolean.valueOf(false))
        );

        this.baseBlock = baseBlockState.getBlock();
        this.baseBlockState = baseBlockState;
        this.collisionShapes = createShapes();
        this.boundingShapes = createShapes();
    }

    protected VoxelShape[] createShapes() {
        VoxelShape[] voxelShapes = new VoxelShape[] {
                VoxelShapes.union(NW_LEG_SHAPE, NE_LEG_SHAPE, SW_LEG_SHAPE, SE_LEG_SHAPE), // None 0b0000
                VoxelShapes.union(NW_LEG_SHAPE, NE_LEG_SHAPE), // South                            0b0001
                VoxelShapes.union(NE_LEG_SHAPE, SE_LEG_SHAPE), // West                             0b0010
                NE_LEG_SHAPE, // South + West                                                      0b0011
                VoxelShapes.union(SE_LEG_SHAPE, SW_LEG_SHAPE), // North                            0b0100
                VoxelShapes.empty(), // North + South                                              0b0101
                SE_LEG_SHAPE, // North + West                                                      0b0110
                VoxelShapes.empty(), // North + South + West                                       0b0111
                VoxelShapes.union(SW_LEG_SHAPE, NW_LEG_SHAPE), // East                             0b1000
                NW_LEG_SHAPE, // East + South                                                      0b1001
                VoxelShapes.empty(), // East + West                                                0b1010
                VoxelShapes.empty(), // East + Wast + South                                        0b1011
                SW_LEG_SHAPE, // East + North                                                      0b1100
                VoxelShapes.empty(), // East + North + South                                       0b1101
                VoxelShapes.empty(), // East + North + West                                        0b1110
                VoxelShapes.empty()  // East + North + West + South                                0b1111
        };

        for(int i = 0; i < 16; i++) {
            voxelShapes[i] = VoxelShapes.union(TOP_SHAPE, voxelShapes[i]);
        }

        return voxelShapes;
    }

    public boolean canConnect(BlockState state) {
        return state.isIn(OSHTags.Blocks.TABLE_BLOCKS);
    }

    private static int getDirectionMask(Direction dir) {
        return 1 << dir.getHorizontal();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.boundingShapes[this.getShapeIndex(state)];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collisionShapes[this.getShapeIndex(state)];
    }

    protected int getShapeIndex(BlockState state) {
        return this.SHAPE_INDEX_CACHE.computeIntIfAbsent(state, statex -> {
            int i = 0;
            if ((Boolean)statex.get(NORTH)) {
                i |= getDirectionMask(Direction.NORTH);
            }

            if ((Boolean)statex.get(EAST)) {
                i |= getDirectionMask(Direction.EAST);
            }

            if ((Boolean)statex.get(SOUTH)) {
                i |= getDirectionMask(Direction.SOUTH);
            }

            if ((Boolean)statex.get(WEST)) {
                i |= getDirectionMask(Direction.WEST);
            }

            return i;
        });
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.east();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        BlockState blockState = blockView.getBlockState(blockPos2);
        BlockState blockState2 = blockView.getBlockState(blockPos3);
        BlockState blockState3 = blockView.getBlockState(blockPos4);
        BlockState blockState4 = blockView.getBlockState(blockPos5);
        return super.getPlacementState(ctx)
                .with(NORTH, Boolean.valueOf(this.canConnect(blockState)))
                .with(EAST, Boolean.valueOf(this.canConnect(blockState2)))
                .with(SOUTH, Boolean.valueOf(this.canConnect(blockState3)))
                .with(WEST, Boolean.valueOf(this.canConnect(blockState4)))
                .with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));
    }

    @Override
    protected MapCodec<? extends TableBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getAxis().getType() == Direction.Type.HORIZONTAL
                ? state.with(
                (Property)FACING_PROPERTIES.get(direction),
                Boolean.valueOf(this.canConnect(neighborState))
        )
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
