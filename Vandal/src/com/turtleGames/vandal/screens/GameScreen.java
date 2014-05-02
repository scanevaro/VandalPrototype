package com.turtleGames.vandal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandal.Vandal;
import com.turtleGames.vandal.collections.Targets;
import com.turtleGames.vandal.entities.Background;
import com.turtleGames.vandal.entities.ImpactSetter;
import com.turtleGames.vandal.entities.Projectile;
import com.turtleGames.vandal.entities.Target;

public class GameScreen implements Screen {

	Vandal game;
	PerspectiveCamera perspCam;
	OrthographicCamera orthoTargetsCam;
	OrthographicCamera orthoBackgroundCam;
	SpriteBatch batcher;
	Model floorModel;
	ModelBatch modelBatch;
	public Array<ModelInstance> instances = new Array<ModelInstance>();

	Background background;
	Targets targets;
	ImpactSetter impactSetter;
	Projectile projectile;

	boolean loading;

	public GameScreen(Vandal game) {
		this.game = game;

		initiateModelBatch();
		initiatePerspCamera();
		initiateOrthoCamera();
		initiateSpriteBatcher();
		initiateBackground();
		initiateFloorModel();
		initiateTargets();
		initiateImpactSetter();
		initiateProjectile();

		loading = true;
	}

	private void initiateModelBatch() {
		modelBatch = new ModelBatch();
	}

	private void initiatePerspCamera() {
		perspCam = new PerspectiveCamera(100, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		perspCam.near = 0.1f;
		perspCam.far = 1000f;
		perspCam.position.set(-60, 15, 0);
		perspCam.lookAt(-20, -10, 0);
		perspCam.update();
	}

	private void initiateOrthoCamera() {
		orthoTargetsCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		orthoTargetsCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		orthoTargetsCam.update();

		orthoBackgroundCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		orthoBackgroundCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		orthoBackgroundCam.update();
	}

	private void initiateSpriteBatcher() {
		batcher = new SpriteBatch();
	}

	private void initiateBackground() {
		background = new Background(0, 30,
				(Texture) game.assets.get("data/springGrass.png"));
	}

	private void initiateFloorModel() {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		MeshPartBuilder part = builder.part("floor", GL20.GL_TRIANGLES,
				Usage.Position | Usage.TextureCoordinates | Usage.Normal,
				new Material());
		part.rect(-100, 0, -200, -100, 0, 200, 100, 0, 200, 100, 0, -200, 0, 1,
				0);
		floorModel = builder.end();

		floorModel.materials.get(0).set(
				TextureAttribute.createDiffuse(game.assets.get(
						"data/grassStripe.png", Texture.class)));
		instances.add(new ModelInstance(floorModel));
	}

	private void initiateTargets() {
		targets = new Targets(7, (Texture) game.assets.get("data/kid50.png"));
	}

	private void initiateImpactSetter() {
		impactSetter = new ImpactSetter();
	}

	private void initiateProjectile() {
		projectile = new Projectile(game.assets.get("data/projectile.png",
				Texture.class));
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}

	private void update(float delta) {
		if (Gdx.input.justTouched()) {

			if (!projectile.isUpdate())
				impactSetter.setShooting(false);

			if (!impactSetter.isActivate() && !impactSetter.isShooting())
				impactSetter.setActivate(true);
			else if (!impactSetter.isShooting()) {
				impactSetter.setShooting(true);
				projectile
						.setImpactSpot(impactSetter.position.set(
								Gdx.graphics.getWidth() / 2
										- impactSetter.position.x,
								Gdx.graphics.getHeight() / 2
										- impactSetter.position.y,
								Gdx.graphics.getHeight() / 2
										- impactSetter.position.z));
				projectile.setUpdate(true);
				projectile.prepare();
				impactSetter.setActivate(false);
			}
		}

		updateCollisionSetter(delta);
		updateProjectile(delta);
		updateTargets(delta);
		updateCamera();
		checkCollitions();
	}

	private void updateCollisionSetter(float delta) {
		impactSetter.update(delta);
	}

	private void updateProjectile(float delta) {
		projectile.update(delta);
	}

	private void updateTargets(float delta) {
		targets.update(delta);
	}

	private void updateCamera() {
		orthoTargetsCam.update();
		perspCam.update();

		if (projectile.isUpdate()) {
			orthoTargetsCam.zoom -= 0.01f;
			orthoTargetsCam.position.y += 0.5f;

			perspCam.position.x += 0.5f;
		} else if (projectile.stateTime >= 0) {
			orthoTargetsCam.zoom += 0.01f;
			orthoTargetsCam.position.y -= 0.5f;

			perspCam.position.x -= 0.5f;
		} else {
			orthoTargetsCam.zoom = 1;
			orthoTargetsCam.position.y = Gdx.graphics.getHeight() / 2;

			perspCam.position.x = -60;
		}
	}

	private void checkCollitions() {
		if (impactSetter.isShooting() && projectile.stateTime >= 0.5f) {
			for (int i = 0; i < targets.targets.size; i++) {
				Target target = targets.targets.get(i);
				if (target.spacePos.z <= projectile.spacePos.z + 8
						&& target.spacePos.z >= projectile.spacePos.z - 8)
					if (Intersector.overlaps(projectile.bounds, target.bounds))
						if (target.spacePos.z <= projectile.spacePos.z + 32
								&& target.spacePos.z >= projectile.spacePos.z - 32) {
							targets.targets.removeIndex(i);
							projectile.setUpdate(false);
						}
			}
		}
	}

	private void draw(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batcher.setProjectionMatrix(orthoBackgroundCam.combined);
		batcher.enableBlending();
		batcher.begin();

		drawBackground();

		batcher.end();

		draw3DModels();
		drawCollisionSetter();

		batcher.setProjectionMatrix(orthoTargetsCam.combined);
		batcher.begin();

		drawTargets();

		batcher.setProjectionMatrix(orthoBackgroundCam.combined);

		drawProjectile();

		batcher.setProjectionMatrix(orthoTargetsCam.combined);

		drawBush();
		drawKid();

		batcher.end();
	}

	private void drawBackground() {
		batcher.draw((Texture) background.texture, background.position.x,
				background.position.y, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
	}

	private void draw3DModels() {
		modelBatch.begin(perspCam);

		modelBatch.render(instances);

		modelBatch.end();
	}

	private void drawCollisionSetter() {
		impactSetter.draw();
	}

	private void drawTargets() {
		targets.render(batcher);
	}

	private void drawProjectile() {
		projectile.draw(batcher);
	}

	private void drawBush() {
		batcher.draw((Texture) game.assets.get("data/grassBackground.png"), 0,
				0, Gdx.graphics.getWidth(), 300);
	}

	private void drawKid() {
		batcher.draw((Texture) game.assets.get("data/kid50.png"),
				Gdx.graphics.getWidth() / 2 - 25, 0, 50, 100);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batcher.dispose();
		impactSetter.dispose();
		floorModel.dispose();
		modelBatch.dispose();
	}
}