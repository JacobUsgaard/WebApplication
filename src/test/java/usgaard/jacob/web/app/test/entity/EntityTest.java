package usgaard.jacob.web.app.test.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import usgaard.jacob.web.app.entity.BaseEntity;

public interface EntityTest<Entity extends BaseEntity> {

	public Entity create();

	public default Collection<Entity> createAll(@NotNull int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Number cannot be less than zero");
		}

		ArrayList<Entity> list = new ArrayList<>(number);
		for (int i = 0; i < number; i++) {
			list.add(create());
		}
		return list;
	}
}
