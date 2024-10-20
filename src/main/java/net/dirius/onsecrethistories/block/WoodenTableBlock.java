package net.dirius.onsecrethistories.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class WoodenTableBlock extends HorizontalConnectingBlock {
    public static final MapCodec<WoodenTableBlock> CODEC = createCodec(WoodenTableBlock::new);
    private final VoxelShape[] cullingShapes;

    public WoodenTableBlock(AbstractBlock.Settings settings) {
        super(2.0F, 2.0F, 16.0F, 16.0F, 16.0F, settings);
        this.setDefaultState(
                this.stateManager
                        .getDefaultState()
                        .with(NORTH, Boolean.valueOf(false))
                        .with(EAST, Boolean.valueOf(false))
                        .with(SOUTH, Boolean.valueOf(false))
                        .with(WEST, Boolean.valueOf(false))
                        .with(WATERLOGGED, Boolean.valueOf(false))
        );
        this.cullingShapes = this.createShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return this.cullingShapes[this.getShapeIndex(state)];
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    public boolean canConnect(BlockState state) {
        Block block = state.getBlock();
        return block instanceof WoodenTableBlock;
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
    protected MapCodec<? extends HorizontalConnectingBlock> getCodec() {
        return CODEC;
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
