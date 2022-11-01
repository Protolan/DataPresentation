package Data;

public class AddressData {

    private char[] _name = new char[20];
    private char[] _address = new char[50];

    private int _nameLastIndex;
    private int _addressLastIndex;

    public AddressData(String name, String address) {
        Construct(name.toCharArray(), address.toCharArray());
    }
    public AddressData(char[] name, char[] address) {
        Construct(name, address);
    }


    public int getNameLength() {
        return _nameLastIndex;
    }
    public int getAddressLength() {
        return _addressLastIndex;
    }

    public char[] getName() {return  _name;}

    public char[] getAddress() {return _address;}

    public void setAddress(char[] address) {
        _address = new char[50];
        for (int i = 0; i < address.length; i++)
            _address[i] = address[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressData Data = (AddressData) o;

        if(Data.getNameLength() != getNameLength() || Data.getAddressLength() != getAddressLength()) {
            return false;
        }

        for (int i = 0; i < getNameLength(); i++) {
            if (_name[i] != Data._name[i]) return false;
        }

        for (int i = 0; i < getAddressLength(); i++) {
            if (_address[i] != Data._address[i]) return false;
        }

        return true;
    }
    public void printData() {
        System.out.print("Name: ");
        for (char c : _name) {
            if (c != 0) {
                System.out.print(c);
            }
        }

        System.out.print(" | Address: ");
        for (char c : _address) {
            if (c != 0) {
                System.out.print(c);
            }
        }
        System.out.println();
    }


    private void Construct(char[] name, char[] address) {
        for (int i = 0; i < name.length; i++) {
            this._name[i] = name[i];
        }
        for (int i = 0; i < address.length; i++) {
            this._address[i] = address[i];
        }
        _nameLastIndex = name.length;
        _addressLastIndex = address.length;
    }





}
