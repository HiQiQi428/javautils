package org.luncert.objpool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestKryo {

    class User {
        int id;
        public User(int id) { this.id = id; }
        public void setId(int id) { this.id = id; }
        public int getId() { return id; }
    }

    class UserSerializer extends Serializer<User> {

		@Override
		public void write(Kryo kryo, Output output, User object) {
			output.writeInt(object.getId());
		}

		@Override
		public User read(Kryo kryo, Input input, Class<User> type) {
			return new User(input.readInt());
		}

    }

    @Test
    public void test() throws FileNotFoundException {
        String path = "/home/lijingwei/Desktop/user.bin";
        Kryo kryo = new Kryo();
        kryo.register(User.class, new UserSerializer());

        User user = new User(123);

        Output output = new Output(new FileOutputStream(path));
        kryo.writeObject(output, user);
        output.close();

        Input input = new Input(new FileInputStream(path));
        user = kryo.readObject(input, User.class);
        input.close();

        System.out.println(user.getId());
    }
}