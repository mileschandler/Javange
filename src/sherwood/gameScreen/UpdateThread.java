package sherwood.gameScreen;

import java.awt.Graphics2D;
import java.util.function.Supplier;

import sherwood.gameScreen.inputs.keyboard.KeyboardInput;
import sherwood.screenStates.ScreenState;

public class UpdateThread extends Thread {

	private Supplier<ScreenState> screenState;
	private Supplier<Graphics2D> graphics;
	private Supplier<KeyboardInput> input;
	private Supplier<GameScreen> gameScreen;
	private Supplier<UpdateAlgorithm> updateAlgorithm;

	public UpdateThread(Supplier<ScreenState> screenState,
			Supplier<Graphics2D> graphics, Supplier<KeyboardInput> input,
			Supplier<GameScreen> gameScreen,
			Supplier<UpdateAlgorithm> updateAlgorithm) {
		super();
		this.screenState = screenState;
		this.graphics = graphics;
		this.input = input;
		this.gameScreen = gameScreen;
		this.updateAlgorithm = updateAlgorithm;
	}

	@Override
	public void run() {
		screenState.get().init();
		while (true) {
			updateAlgorithm.get().update(screenState.get(), graphics.get(),
					gameScreen.get(), input.get());
		}
	}
}
