import requests, os, random

OCR_LOCAL_URL = "http://127.0.0.1:6000/b"

def parse_captcha_image(img_path, captcha_length = 4):
    try:
        resp = requests.post(
            url=OCR_LOCAL_URL, 
            files={'image_file': open(img_path, 'rb')} , 
            timeout=10
        )
    except requests.exceptions.RequestException:
        return None
    resp = resp.json()
    if resp is None or resp.get("value") is None or not resp.get("value").strip():
        return None # something went wrong
    return resp.get("value")

def parse_captcha_diretory(img_dir):
    files = os.listdir(img_dir)
    random.shuffle(files)
    # I would like this to succeed AT LEAST every once in 10 tries
    for filename in files[:10]:
        if filename.endswith('png') or filename.endswith('jpg'):
            img_path = os.path.join(img_dir, filename)
            ret = parse_captcha_image(img_path)
            print(f"{img_path} parsed {ret}")
    return

if __name__ == "__main__":
    parse_captcha_diretory("/root/ZhihuScraper/captchas/")