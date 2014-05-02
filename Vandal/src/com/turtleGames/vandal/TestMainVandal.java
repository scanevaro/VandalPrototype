package com.turtleGames.vandal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.turtleGames.vandal.classes.BaseG3dHudTest;
import com.turtleGames.vandal.collections.Targets;

public class TestMainVandal extends BaseG3dHudTest {
	Model floorModel;
	ShapeRenderer shapeRenderer;

	OrthographicCamera orthoCam;
	SpriteBatch batcher;

	Targets targets;

	@Override
	public void create() {
		super.create();

		orthoCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		orthoCam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		orthoCam.update();

		batcher = new SpriteBatch();

		perspCam.position.set(-60, 15, 0);
		perspCam.lookAt(-20, -10, 0);
		perspCam.update();

		assets.load("data/springGrass.png", Texture.class);
		assets.load("data/grassStripe.png", Texture.class);
		assets.load("data/grassBackground.png", Texture.class);
		assets.load("data/kid50.png", Texture.class);

		loading = true;

		shapeRenderer = new ShapeRenderer();

		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		MeshPartBuilder part = builder.part("floor", GL20.GL_TRIANGLES,
				Usage.Position | Usage.TextureCoordinates | Usage.Normal,
				new Material());
		part.rect(-100, 0, -200, -100, 0, 200, 100, 0, 200, 100, 0, -200, 0, 1,
				0);
		floorModel = builder.end();

		// targets = new Targets(7, (Texture) assets.get("data/kid.png"));
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
		batcher.setProjectionMatrix(orthoCam.combined);

		batcher.enableBlending();
		batcher.begin();

		if (assets.update())
			batcher.draw((Texture) assets.get("data/springGrass.png"), 0, 30,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batcher.end();

		super.render();

		batcher.setProjectionMatrix(orthoCam.combined);

		// draw line that sets the collision
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(1, 1, 0, 1);
		shapeRenderer.triangle(
				// left poing
				Gdx.graphics.getWidth() / 2 - 80,
				Gdx.graphics.getHeight() / 2 - 100,
				// right point
				Gdx.graphics.getWidth() / 2 + 80,
				Gdx.graphics.getHeight() / 2 - 100,
				// top point
				Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
		shapeRenderer.end();

		batcher.enableBlending();
		batcher.begin();

		if (assets.update()) {
			// Targets
			targets.update(Gdx.graphics.getDeltaTime());
			targets.render(batcher);

			// Bush and kid
			batcher.draw((Texture) assets.get("data/grassBackground.png"), 0,
					0, Gdx.graphics.getWidth(), 300);
			batcher.draw((Texture) assets.get("data/kid50.png"),
					Gdx.graphics.getWidth() / 2 - 25, 0, 50, 100);
		}

		batcher.end();
	}

	@Override
	protected void render(ModelBatch batch, Array<ModelInstance> instances) {
		batch.render(instances);
	}

	// @Override
	// protected void getStatus(StringBuilder stringBuilder) {
	// super.getStatus(stringBuilder);
	// }

	@Override
	protected void onLoaded() {
		floorModel.materials.get(0).set(
				TextureAttribute.createDiffuse(assets.get(
						"data/grassStripe.png", Texture.class)));
		instances.add(new ModelInstance(floorModel));
		targets = new Targets(7, (Texture) assets.get("data/kid50.png"));
	}

	@Override
	public void dispose() {
		super.dispose();
		floorModel.dispose();
		batcher.dispose();
	}
}